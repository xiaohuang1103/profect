package com.example.project01.service;

import com.example.project01.model.Require;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@AllArgsConstructor
public class RequireService {
    @Autowired
    private final SqlSession sqlSession;
    public Require select(Integer id) {
        if (id != null) {
            return sqlSession.selectOne("app-require.select", id);
        } else {
            return null;
        }
    }
    public List<Map<String, Object>> findAllWithPagination(int pageNo, int pageSize, Map<String, Object> params) {
        int start = (pageNo - 1) * pageSize;
        params.put("start", start);
        params.put("pageSize", pageSize);
        if (params.containsKey("type")) {
            String type = (String) params.get("type");
            if ("iOS".equalsIgnoreCase(type)) {
                params.put("type", "%iOS%");
            } else if ("Android".equalsIgnoreCase(type)) {
                params.put("type", "%Android%");
            }
        }
        if (params.containsKey("forceUpdateStartTime")) {
            params.put("forceUpdateStartTime", params.get("forceUpdateStartTime"));
        }
        if (params.containsKey("forceUpdateEndTime")) {
            params.put("forceUpdateEndTime", params.get("forceUpdateEndTime"));
        }
        return sqlSession.selectList("app-require.selectAllWithPagination", params);
    }

    public void update(Require require) {
        Require existingRequire = sqlSession.selectOne("app-require.select", require.getId());
        if (existingRequire != null) {
            sqlSession.update("app-require.update", require.toMap());
        } else {
            throw new IllegalArgumentException("该 id 不存在，请先新增");
        }
    }

    @Transactional
    public void insert(Require require) {
        sqlSession.insert("app-require.insertOrUpdate", require.toMap());
        log.info("Require inserted successfully");
    }

    public Integer delete(Integer id) {
        Require existingRequire = sqlSession.selectOne("app-require.select", id);
        if (existingRequire != null) {
            return sqlSession.delete("app-require.delete", id);
        } else {
            throw new IllegalArgumentException("该资源不存在");
        }
    }
}