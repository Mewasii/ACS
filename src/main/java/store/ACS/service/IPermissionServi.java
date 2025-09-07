package store.ACS.service;

import java.util.List;

import store.ACS.dto.request.PermissionRequest;
import store.ACS.dto.response.PermissionResponse;

public interface IPermissionServi {
	public PermissionResponse create(PermissionRequest request);

	public List<PermissionResponse> getAll();

	void delete(String permission);
}
