package com.akali.provider.user.admin.service;

import com.akali.common.code.ClientCode;
import com.akali.common.code.CommonCode;
import com.akali.common.dto.admin.ClientWithPermissionDTO;
import com.akali.common.dto.admin.PermissionDTO;
import com.akali.common.dto.admin.PermissionToClientDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.user.admin.bean.AmsClient;
import com.akali.provider.user.admin.dao.ClientDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName ClientServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class ClientServiceImpl implements ClientService{
    @Autowired
    private ClientDao clientDao;


    /**
     * 添加服务接口的权限拦截
     *
     * @param p2cDTO
     * @return
     */
    @Override
    public DubboResponse<Void> addPermissionToClient(PermissionToClientDTO p2cDTO) {
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 获取服务接口权限拦截信息
     *
     * @param clientId
     * @return
     */
    @Override
    public DubboResponse<ClientWithPermissionDTO> getClientWithPermission(Long clientId) {
        Optional<AmsClient> opt = clientDao.findById(clientId);
        if (!opt.isPresent()) {
            DubboResponse.FAIL(ClientCode.CLIENT_NOT_EXIST);
        }
        AmsClient client = opt.get();
        ClientWithPermissionDTO result = new ClientWithPermissionDTO();
        result.setClientId(client.getId());
        result.setClientName(client.getClientName());
        List<PermissionDTO> permissions = client.getPermissions().stream()
                .map(p -> new PermissionDTO(p.getId(), p.getPermissionName(), p.getUri(),p.getAuthoritySign()))
                .collect(Collectors.toList());
        result.setPermissions(permissions);
        return DubboResponse.SUCCESS(result);
    }
}
