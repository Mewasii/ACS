package store.ACS.service;

import java.util.List;

import store.ACS.dto.request.RoleRequest;
import store.ACS.dto.response.RoleResponse;

public interface IRoleService  {
	public RoleResponse create(RoleRequest request);
	public List<RoleResponse> getAll();
	public void delete(String role);
}
