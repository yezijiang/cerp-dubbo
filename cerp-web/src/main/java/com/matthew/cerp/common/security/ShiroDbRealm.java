package com.matthew.cerp.common.security;

import com.matthew.cerp.common.util.Encodes;
import com.matthew.cerp.entity.Staff;
import com.matthew.cerp.service.RoleRightService;
import com.matthew.cerp.service.StaffRoleService;
import com.matthew.cerp.service.StaffService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-07-05 13:40
 */
public class ShiroDbRealm extends AuthorizingRealm {
    @Autowired
    protected StaffService staffService;
    @Autowired
    protected StaffRoleService staffRoleService;
    @Autowired
    protected RoleRightService roleRightService;

    /**
     * 抽授权查询回调函数，进行鉴权但缓存中无用户的授权信息时调用
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Staff loginStaff = (Staff)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(loginStaff != null){
            String staffId = String.valueOf(loginStaff.getStaffId());
            //1.获取角色
            List<String> roles = staffRoleService.getRolesByStaffId(staffId);
            if(roles != null & roles.size() > 0){
                info.addRoles(roles);
                //2.根据角色获取权限
                for(String role:roles){
                    List<String> perms = roleRightService.getRightCodesByRoleId(role);
                    info.addStringPermissions(perms);
                }
            }

        }

        return info;
    }

    /**
     * 认证回调函数，登录时调用
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        Staff staff= staffService.findByLoginAccount(token.getUsername());
        if(staff != null){
            byte[] salt = Encodes.decodeHex(staff.getSalt());
            return new SimpleAuthenticationInfo(staff,staff.getLoginPassword(),new SerializableByteSource(salt),getName());
        }else{
            return null;
        }
    }
}
