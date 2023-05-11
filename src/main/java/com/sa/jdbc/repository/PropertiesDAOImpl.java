package com.sa.jdbc.repository;

import com.sa.jdbc.exceptions.NotFoundException;
import com.sa.jdbc.model.Properties;
import com.sa.jdbc.service.PropertiesDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PropertiesDAOImpl implements PropertiesDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    private Environment env;
    
    
    @Override
    public List<Properties> findAll() throws Exception {
        try {
            String sql = env.getProperty("query.select1.properties");
            
            List<Properties> registros = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Properties.class));
            if(registros.isEmpty()) {
                throw new NotFoundException("El registro no existe");
            }
            return registros; 
            
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    
    //Busqueda general paginada
    @Override
    public Page<Properties> findAll(Pageable pageable) throws Exception {
        try {
            String sql = env.getProperty("query.select1.properties");
            
            List<Properties> entities = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Properties.class));
            int total = entities.size();
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), total);
            Page<Properties> page = new PageImpl<>(entities.subList(start, end), pageable, total);
            
            return page;
            
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    
    @Override
    public Properties findById(Long id) throws Exception{
        
        try {
            String sql = env.getProperty("query.select2.properties");
            
            Properties saved = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Properties.class));
            if (saved == null) {
                throw new NotFoundException("El registro no existe");
            }
            return saved;
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
   
    
    //Busqueda filtrada
    @Override
    public List<Properties> findByFilter(String filter) throws Exception{
        
        try {
            String sql = env.getProperty("query.filter.properties");
            
            List<Properties> result = jdbcTemplate.query(sql, new Object[] {"%" + filter + "%"}, new BeanPropertyRowMapper<>(Properties.class));
            if(result.isEmpty()) {
                throw new NotFoundException("No hay registros que coincidan");
            } 
            return result;
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    
    //Busqueda filtrada y paginada
    @Override
    public Page<Properties> findByFilterPaged(Pageable pageable, String filter) throws Exception {
        
        try {
            String sql = env.getProperty("query.filter.properties");
            
            List<Properties>result = jdbcTemplate.query(sql, new Object[] {"%" + filter + "%"}, new BeanPropertyRowMapper<>(Properties.class));
            if(result.isEmpty()) {
                throw new NotFoundException("No hay registros que coincidan");
            }
            int total = result.size();
            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), total);
            Page<Properties> page = new PageImpl<>(result.subList(start, end), pageable, total);
            
            return page;
            
        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    //Insert que solo devuelve un int (es mínimamente más rápido que el siguiente)
    @Override
    public int save(Properties proper) throws Exception {
        
        try {
            String sql = env.getProperty("query.insert.properties");
            
            return jdbcTemplate.update(sql, new Object[]{proper.getCopay(),
                            proper.getMedicalGuide(), proper.getInternation(),
                            proper.getDoctorToHome(), proper.getOdontology(),
                            proper.getOrthodontics(), proper.getRefund()});
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    //Insert que devuelve el objeto creado al guardar.
    @Override
    public Properties save2(Properties proper) throws Exception{
        
        String sql = env.getProperty("query.insert.properties");
        
        try {
            int saved = jdbcTemplate.update(sql, new Object[]{proper.getCopay(),
                            proper.getMedicalGuide(), proper.getInternation(),
                            proper.getDoctorToHome(), proper.getOdontology(),
                            proper.getOrthodontics(), proper.getRefund()});
        
            String sql2 = env.getProperty("query.select2.properties");
            Properties savedProperties = jdbcTemplate.queryForObject(sql2,
                            new Object[]{jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class)},
                            new BeanPropertyRowMapper<>(Properties.class));
        
            return savedProperties;
            
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

   
    @Override
    public Properties update(Long id, Properties proper) throws Exception{
        
        try {
            String sql = env.getProperty("query.update.properties");
            
            int saved = jdbcTemplate.update(sql, new Object[]{
                            proper.getCopay(),
                            proper.getMedicalGuide(),
                            proper.getInternation(),
                            proper.getDoctorToHome(),
                            proper.getOdontology(),
                            proper.getOrthodontics(),
                            proper.getRefund(),
                            id
            });         
            String sql2 = env.getProperty("query.select2.properties");
            Properties savedProperties = jdbcTemplate.queryForObject(sql2, new Object[]{id}, new BeanPropertyRowMapper<>(Properties.class));
            return savedProperties;
            
        } catch(EmptyResultDataAccessException e) {
            throw new NotFoundException("El registro no fue encontrado");
        }
    }

    @Override
    public int delete(Long id) throws Exception{
        
        try {
            String sql = env.getProperty("query.delete.properties");
            
            int deleted = jdbcTemplate.update(sql, id);
            if (deleted == 0) {
                throw new NotFoundException("El registro no existe");
            }
            return deleted;
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
    

