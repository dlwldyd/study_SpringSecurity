package springsecurity.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import springsecurity.core.domain.entity.Resources;
import springsecurity.core.repository.ResourcesRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
public class SecurityResourceService {

    private final ResourcesRepository resourcesRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();
        List<Resources> resourcesList = resourcesRepository.findAllResources();

        resourcesList.forEach(resources -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();
            resources.getRoleSet().forEach(resourcesRole ->
                configAttributeList.add(new SecurityConfig(resourcesRole.getRole().getRoleName()))
            );
            result.put(new AntPathRequestMatcher(resources.getResourceName()), configAttributeList);
        });
        return result;
    }
}
