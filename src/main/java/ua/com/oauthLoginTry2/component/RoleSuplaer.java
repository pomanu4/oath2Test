package ua.com.oauthLoginTry2.component;

import ua.com.oauthLoginTry2.entity.Role;

public class RoleSuplaer {
	
	private static Role[] roles = new Role[] {Role.ROLE_USER, Role.ROLE_ADMIN};
	
	public static Role[] supplyRoles() {
		return roles;
	}
}
