package br.com.elasticsearch.teste.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.security.ExpressionRoleMapping;
import org.elasticsearch.client.security.GetRoleMappingsRequest;
import org.elasticsearch.client.security.GetRoleMappingsResponse;
import org.elasticsearch.client.security.GetRolesRequest;
import org.elasticsearch.client.security.GetRolesResponse;
import org.elasticsearch.client.security.PutRoleMappingRequest;
import org.elasticsearch.client.security.PutRoleMappingResponse;
import org.elasticsearch.client.security.RefreshPolicy;
import org.elasticsearch.client.security.support.expressiondsl.RoleMapperExpression;
import org.elasticsearch.client.security.support.expressiondsl.expressions.AnyRoleMapperExpression;
import org.elasticsearch.client.security.support.expressiondsl.fields.FieldRoleMapperExpression;
import org.elasticsearch.client.security.user.privileges.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RestController {
	
	@Autowired
	private ESConfiguration es;
	
	@RequestMapping("/")
	@ResponseBody
	private boolean iniciar() throws IOException {		
		RoleMapperExpression rules = AnyRoleMapperExpression.builder()
				.addExpression(FieldRoleMapperExpression.ofUsername("teste","wallace","bruna","Bryan"))
				.addExpression(FieldRoleMapperExpression.ofGroups("cn=GAPL,dn=dc,dn=br","cn=GAPL_INFRA_APOIO,dn=dc,dn=br","cn=SUPORTE,dn=dc,dn=br"))
				.build();
		PutRoleMappingRequest request1 = new PutRoleMappingRequest("mapping-example", true,
				Arrays.asList("superuser", "kibana_user"), rules, Collections.EMPTY_MAP,  RefreshPolicy.NONE);
		PutRoleMappingResponse response1 = es.restClientHighLevel().security().putRoleMapping(request1, RequestOptions.DEFAULT);
		boolean isCreated = response1.isCreated();
		return isCreated;
	}
	
	@RequestMapping("/roles")
	@ResponseBody
	private List<Role> getRole() throws IOException{
		GetRolesRequest request = new GetRolesRequest("superuser","kibana_user");
		GetRolesResponse response = es.restClientHighLevel().security().getRoles(request, RequestOptions.DEFAULT);
		List<Role> roles = response.getRoles();
		return roles;
	}
	
	@RequestMapping("/rolemapping")
	@ResponseBody
	private List<ExpressionRoleMapping> getRoleMapping() throws IOException {
		GetRoleMappingsRequest request = new GetRoleMappingsRequest("mapping-example");
		GetRoleMappingsResponse response = es.restClientHighLevel().security().getRoleMappings(request, RequestOptions.DEFAULT);
		List<ExpressionRoleMapping> mappings = response.getMappings();
		return mappings;
		
	}
	
}
