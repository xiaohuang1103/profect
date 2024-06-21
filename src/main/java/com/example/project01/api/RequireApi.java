package com.example.project01.api;

import com.example.project01.model.Require;
import com.example.project01.service.RequireService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.logging.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/app")
public class RequireApi {
    private RequireService requireService;
    private static final Logger logger = Logger.getLogger(RequireApi.class.getName());

    @DeleteMapping(value = "/require/sc")
    @Operation(summary = "删除")
    @SuppressWarnings("unused")
    public ResponseEntity<?> delRequire(@RequestParam(value = "id", required = false) Integer id) {
        try {
            requireService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(value = "/require/cx")
    @Operation(summary = "查询")
    @SuppressWarnings("unused")
    public ResponseEntity<?> getRequire(@RequestParam(value = "id", required = false) Integer id,
                                        @RequestParam(value = "pageNo", required = false) Integer pageNo,
                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                        @RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "forceUpdateStartTime", required = false) String forceUpdateStartTime,
                                        @RequestParam(value = "forceUpdateEndTime", required = false) String forceUpdateEndTime) {
        if (id != null) {
            Require require = requireService.select(id);
            if (require != null) {
                return ResponseEntity.ok().body(require);
            } else {
                return ResponseEntity.ok().body("No require found with ID: " + id);
            }
        } else {
            int start = (pageNo != null && pageSize != null) ? (pageNo - 1) * pageSize : 0;
            Map<String, Object> params = new HashMap<>();
            params.put("start", start);
            params.put("pageSize", pageSize != null ? pageSize : 10);
            if (type != null) {
                params.put("type", type);
            }
            if (forceUpdateStartTime != null) {
                params.put("forceUpdateStartTime", forceUpdateStartTime);
            }
            if (forceUpdateEndTime != null) {
                params.put("forceUpdateEndTime", forceUpdateEndTime);
            }
            List<Map<String, Object>> requireList = requireService.findAllWithPagination(start, pageSize != null ? pageSize : 10, params);
            return ResponseEntity.ok().body(requireList);
        }
    }

    @PostMapping(value = "/require/xz")
    @Operation(summary = "新增")
    @SuppressWarnings("unused")
    public ResponseEntity<?> postRequire(@RequestBody Require require) {
        Integer id = require.getId();
        Require existingRequire = requireService.select(id);
        if (existingRequire != null) {
            requireService.update(require);
            return ResponseEntity.ok().body("Require with ID " + id + " updated successfully");
        } else {
            requireService.insert(require);
            return ResponseEntity.ok().body("Not find ID :" + id + ","+"Give you inserted new id");
        }
    }

    @PutMapping(value = "/require/xg")
    @Operation(summary = "修改")
    @SuppressWarnings("unused")
    public ResponseEntity<?> putRequire(@RequestBody Require require) {
        Require existingRequire = requireService.select(require.getId());
        if (existingRequire != null) {
            if ("iOS".equals(require.getType())) {
                logger.info("Processing iOS type");
            } else if ("Android".equals(require.getType())) {
                logger.info("Processing Android type");
            } else {
                logger.warning("Unknown type: " + require.getType());
            }
            requireService.update(require);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("该资源不存在");
        }
    }
}