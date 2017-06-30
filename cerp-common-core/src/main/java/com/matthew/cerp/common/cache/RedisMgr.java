package com.matthew.cerp.common.cache;

import com.matthew.cerp.common.dao.CommonDao;
import com.yaoyaohao.framework.redis.ShardedJedisClient;
import com.yaoyaohao.framework.redis.SimpleUtils;
import com.yaoyaohao.framework.redis.exception.RedisClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 根据paramtables.xml配置的整表信息进行整表的缓存加载处理
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-29 13:59
 */
@Component
@SuppressWarnings("unchecked")
public class RedisMgr {
    private static final Logger logger = LoggerFactory.getLogger(RedisMgr.class);

    private final static Map<String, Map<String, String>> tableInfos = new HashMap<String, Map<String, String>>();

    private final static String CONFIG_PATH = "/redis/paramtables.xml";

    private final static String CACHE_PREFIX = "CERP:TABLES:";

    @Autowired
    ShardedJedisClient redisClient;

    @Autowired
    CommonDao commonDao;

    /**
     * 类初始化，将配置加载到私有对象中，避免每次都从xml中加载
     */
    @PostConstruct
    public void init(){
        XMLParser.parse(CONFIG_PATH);
    }

    /**
     * 获取table中的数据，返回List
     *
     * @param tableName	表名
     * @param indexKey		索引字段
     * @param indexValue	索引字段值
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> getList(String tableName, String indexKey, String indexValue) throws Exception {
        TreeMap<String, String> inParam = new TreeMap<String, String>();
        if(indexKey != null && indexKey.length() > 0) {
            String keys[] = indexKey.split(",");
            String values[] = indexValue.split(",");
            if (tableName == null || "".equals(tableName)) {
                throw new RedisClientException("从缓存中获取参数时，tableName不能为空值或者null");
            }

            if (keys.length != values.length) {
                throw new RedisClientException("从缓存中获取参数时，key与value数量不一致");
            }
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] == null || "".equals(keys[i]) || values[i] == null) {
                    throw new RedisClientException("从缓存中获取参数时，keys或者values中不能包含空值或者null");
                }
                inParam.put(keys[i], values[i]);

            }
        }
        return getList(tableName, inParam);
    }

    public <T> List<T> getList(String tableName, String indexKey, String indexValue,ExtractHandler<T> handler) throws Exception{
        List<Map<String,String>> list = getList(tableName, indexKey,indexValue);
        //
        List<T> results = new ArrayList<T>();
        for(Map<String,String> map : list) {
            T t = handler.extract(map);
            results.add(t);
        }
        return results;
    }

    public List<Map<String, String>> getList(String tableName, TreeMap<String, String> inParam) {
        if(inParam == null)
            inParam = new TreeMap<String, String>();
        boolean ifPk = true;
        try {
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            tableName = tableName.toUpperCase();
            Map<String, String> tableInfo = tableInfos.get(tableName);

            if (tableInfo == null) {
                throw new RedisClientException("没有配置" + tableName + "表的缓存信息");
            }

            String vkey = tableName;
            String vvalue = "";
            for (Map.Entry<String, String> entry : inParam.entrySet()) {
                vkey = vkey.concat("_").concat(entry.getKey().trim());
                vvalue = vvalue.concat("_").concat(entry.getValue().trim());
            }
            // 判断查询的范围
            if (!tableInfo.get("TABLE_PK_STR").equals(vkey)) {
                ifPk = false;
            }
            vkey = vkey.concat(vvalue);
            //
            String cacheKey = tableName;
            if (!ifPk) {
                cacheKey = cacheKey.concat("_INDEX");
            }
            String tableIndex = redisClient.hget(CACHE_PREFIX + cacheKey, vkey);
            if (tableIndex != null) {
                if ("".equals(tableIndex)) {
                    return null;
                } else {
                    String[] keys = tableIndex.split("\\$@\\$");
                    for (String key : keys) {
                        Map<String, String> data = redisClient.hgetAll(CACHE_PREFIX + key);
                        if (data != null) {
                            result.add(data);
                        }
                    }
                    return result;
                }
            }
            //若未查询到，则从DB中查找记录并加载到缓存中
            else{
                if(loadParam(tableName, inParam))
                    return getList(tableName, inParam);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RedisClientException(e);
        }
        return null;
    }

    /**
     * 根据索引字段及值获取符合条件的单条记录
     *
     * @param tableName	表名
     * @param indexKey		索引字段
     * @param indexValue	索引字段值
     * @return
     * @throws Exception
     */
    public Map<String, String> getData(String tableName, String indexKey, String indexValue) throws Exception {
        String keys[] = indexKey.split(",");
        String values[] = indexValue.split(",");
        if (tableName == null || "".equals(tableName)) {
            throw new RedisClientException("从缓存中获取参数时，tableName不能为空值或者null");
        }

        if (keys.length != values.length) {
            throw new RedisClientException("从缓存中获取参数时，key与value数量不一致");
        }

        TreeMap<String, String> inParam = new TreeMap<String, String>();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null || "".equals(keys[i]) || values[i] == null) {
                throw new RedisClientException("从缓存中获取参数时，keys或者values中不能包含空值或者null");
            }
            inParam.put(keys[i], values[i]);
        }
        return getData(tableName, inParam);
    }

    public <T> T getData(String tableName, String indexKey, String indexValue,ExtractHandler<T> handler) throws Exception{
        Map<String,String> map = getData(tableName, indexKey,indexValue);
        //
        return handler.extract(map);
    }

    private Map<String, String> getData(String tableName, TreeMap<String, String> inParam) {
        boolean ifPk = true;
        try {
            tableName = tableName.toUpperCase();
            Map<String, String> tableInfo = tableInfos.get(tableName);

            if (tableInfo == null) {
                throw new RedisClientException("没有配置" + tableName + "表的缓存信息");
            }

            String vkey = tableName;
            String vvalue = "";
            for (Map.Entry<String, String> entry : inParam.entrySet()) {
                vkey = vkey.concat("_").concat(entry.getKey().trim());
                vvalue = vvalue.concat("_").concat(entry.getValue().trim());
            }
            if (!tableInfo.get("TABLE_PK_STR").equals(vkey)) {
                ifPk = false;
            }
            vkey = vkey.concat(vvalue);
            //
            String cacheKey = tableName;
            if (!ifPk) {
                cacheKey = cacheKey.concat("_INDEX");
            }
            String tableIndex = redisClient.hget(CACHE_PREFIX + cacheKey, vkey);
            if (tableIndex != null) {
                if ("".equals(tableIndex)) {
                    return null;
                } else {
                    String[] keys = tableIndex.split("\\$@\\$");
                    for (String key : keys) {
                        Map<String, String> data = redisClient.hgetAll(CACHE_PREFIX + key);
                        if (data != null) {
                            return data;
                        }
                    }
                }
            }
            //若不存在则从DB中查找并加载到缓存中
            else{
                if(loadParam(tableName, inParam))
                    return getData(tableName, inParam);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RedisClientException(e);
        }
        return null;
    }

    /**
     * 加载参数，如果按照主键加载参数，将检索数据存放在以tableName为key的hashMap区域。否则将检索数据放在以tableName_INDEX为key的区域。
     *
     * @param tableName
     * @param inParam
     */
    private boolean loadParam(String tableName, TreeMap<String, String> inParam) {
        try{
            tableName = tableName.toUpperCase();
            Map<String, String> tableInfo = tableInfos.get(tableName);

            if (tableInfo == null) {
                throw new RedisClientException("没有配置" + tableName + "表的缓存信息");
            }
            //
            Method method = commonDao.getClass().getMethod(tableName,Map.class);
            List<Map<String, Object>> tableRecords = (List<Map<String, Object>>) method.invoke(commonDao, inParam);
            if(tableRecords != null && tableRecords.size() > 0) {
                loadParam2(tableName, inParam, tableRecords);
                return true;
            }
            else
                return false;
        }catch(Exception e){
            logger.error(e.getMessage());
            throw new RedisClientException(e);
        }
    }

    private void loadParam2(String tableName, TreeMap<String, String> inParam, List<Map<String, Object>> tableRecords)
            throws Exception {
        boolean ifPk = true;
        try {
            Map<String, String> tableInfo = tableInfos.get(tableName);
            String[] txpks = tableInfo.get("TABLE_PK").split(",");

            String cacheValue = "";
            for (int i = 0; i < tableRecords.size(); i++) {
                Map<String, Object> param = (Map<String, Object>) tableRecords.get(i);

                String pkValue = "";
                for (int j = 0; j < txpks.length; j++) {
                    pkValue = pkValue.concat("_").concat(SimpleUtils.getStr(param, txpks[j].trim()).trim());
                }
                String pkKey = tableInfo.get("TABLE_PK_STR").concat(pkValue);

                if (i == 0) {
                    cacheValue = pkKey;
                } else {
                    cacheValue = cacheValue.concat("$@$").concat(pkKey);
                }
                if (ifPk || !redisClient.exists(pkKey)) {
                    Map<String, String> strParam = new HashMap<String, String>();
                    for (Map.Entry<String, Object> entry : param.entrySet()) {
                        strParam.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
                    }
                    redisClient.del(CACHE_PREFIX + pkKey);
                    redisClient.hmset(CACHE_PREFIX + pkKey, strParam);
                    redisClient.hset(CACHE_PREFIX + tableName, pkKey, pkKey);
                }
            }

            if(inParam != null) {
                String vkey = tableName;
                String vvalue = "";
                for (Map.Entry<String, String> entry : inParam.entrySet()) {
                    vkey = vkey.concat("_").concat(entry.getKey().trim());
                    vvalue = vvalue.concat("_").concat(entry.getValue().trim());
                }
                // 如果查询的字段不是表的全主键，则查询索引
                if (!tableInfo.get("TABLE_PK_STR").equals(vkey)) {
                    ifPk = false;
                }
                vkey = vkey.concat(vvalue);

                if (ifPk) {
                    redisClient.hset(CACHE_PREFIX + tableName, vkey, cacheValue);
                } else {
                    redisClient.hset(CACHE_PREFIX + tableName.concat("_INDEX"), vkey, cacheValue);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RedisClientException(e);
        }
    }

    /**
     * 根据表名查询缓存的所有记录
     *
     * @param tableName
     * @return
     */
    public List<Map<String, String>> getAllCached(String tableName) {
        try {
            tableName = tableName.toUpperCase();
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            Map<String, String> tableDatas = redisClient.hgetAll(CACHE_PREFIX + tableName);
            if (tableDatas == null) {
                return result;
            }

            for (Map.Entry<String, String> entry : tableDatas.entrySet()) {
                if (!"".equals(entry.getValue())) {
                    result.add(redisClient.hgetAll(CACHE_PREFIX + entry.getKey()));
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RedisClientException(e);
        }
    }

    /**
     * 根据表名清除缓存
     *
     * @param tableName
     */
    public void clearTable(String tableName) {
        String xTable = tableName.toUpperCase();

        try {
            if (tableInfos.containsKey(xTable)) {
                Map<String, String> m = redisClient.hgetAll(CACHE_PREFIX + xTable);
                if (m != null) {
                    for (Map.Entry<String, String> entry : m.entrySet()) {
                        String[] keys = entry.getValue().split("\\$@\\$");
                        for (String key : keys) {
                            redisClient.del(CACHE_PREFIX + key);
                        }
                    }
                    redisClient.del(CACHE_PREFIX + xTable);
                }
                redisClient.del(CACHE_PREFIX + xTable.concat("_INDEX"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RedisClientException(e);
        }
    }

    /**
     * XML解析，根据配置文件解析的表名及主键信息
     *
     */
    private static class XMLParser {
        private static XPath path;
        private static Document doc;

        private static String getString(Object node, String expression) throws XPathExpressionException {
            return ((String) path.evaluate(expression, node, XPathConstants.STRING));
        }

        private static NodeList getList(Object node, String expression) throws XPathExpressionException {
            return ((NodeList) path.evaluate(expression, node, XPathConstants.NODESET));
        }

        private static Node getNode(Object node, String expression) throws XPathExpressionException {
            return ((Node) path.evaluate(expression, node, XPathConstants.NODE));
        }

        public static void parse(String redisConfig) {
            try {
                DocumentBuilder dbd = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                doc = dbd.parse(XMLParser.class.getResourceAsStream(redisConfig));
                path = XPathFactory.newInstance().newXPath();
                Node rootN = getNode(doc, "paramtables");
                if (null == rootN) {
                    throw new RedisClientException("Invalid xml format, can't find <config> root node!");
                }

                NodeList nodeList = getList(rootN, "table");
                if (null == nodeList || nodeList.getLength() == 0) {
                    throw new RedisClientException("Invalid xml format, can't find <table>!");
                }
                for (int i = 0; i < nodeList.getLength(); ++i) {
                    Node node = nodeList.item(i);
                    String tableName = getString(node, "@tableName");
                    String primaryKeys = getString(node, "@primaryKeys");

                    if (null == tableName || "".equals(tableName.trim())) {
                        throw new RedisClientException(
                                "Invalid xml format, <table> node should have tableName attribute.");
                    }

                    if (null == primaryKeys || "".equals(primaryKeys.trim())) {
                        throw new RedisClientException(
                                "Invalid xml format, <table> node should have primaryKeys attribute.");
                    }

                    String[] txpks = primaryKeys.toUpperCase().split(",");
                    Arrays.sort(txpks);
                    String xTable = tableName.toUpperCase();

                    Map<String, String> tableInfo = new HashMap<String, String>();
                    tableInfo.put("TABLE_NAME", xTable);
                    tableInfo.put("TABLE_PK", SimpleUtils.join(txpks, ","));
                    String tablePkStr = xTable;
                    for (String txpk : txpks) {
                        tablePkStr = tablePkStr.concat("_").concat(txpk.trim());
                    }
                    tableInfo.put("TABLE_PK_STR", tablePkStr);
                    tableInfos.put(xTable, tableInfo);
                }
            } catch (IOException e) {
                throw new RedisClientException("IOException!", e);
            } catch (Exception ex) {
                throw new RedisClientException("Fail to parse redis configure file.", ex);
            }
        }
    }

    public interface ExtractHandler<T> {
        public T extract(Map<String, String> map);
    }
}