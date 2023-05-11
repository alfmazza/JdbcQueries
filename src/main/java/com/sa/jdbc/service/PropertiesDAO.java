package com.sa.jdbc.service;

import com.sa.jdbc.model.Properties;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PropertiesDAO {
    
    public List<Properties> findAll() throws Exception;
    public Page<Properties> findAll(Pageable pageable) throws Exception;
    public Properties findById(Long id) throws Exception;
    public int save(Properties proper) throws Exception;
    public Properties save2(Properties proper) throws Exception;
    public Properties update(Long id, Properties proer) throws Exception;
    public int delete(Long id) throws Exception;
    public List<Properties> findByFilter(String filter) throws Exception;
    public Page<Properties> findByFilterPaged(Pageable pageable, String filter) throws Exception;
    
}
