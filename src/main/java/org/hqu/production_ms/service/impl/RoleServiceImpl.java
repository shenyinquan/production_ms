package org.hqu.production_ms.service.impl;

import java.util.List;

import org.hqu.production_ms.domain.authority.SysRole;
import org.hqu.production_ms.domain.authority.SysRoleExample;
import org.hqu.production_ms.domain.authority.SysRolePermission;
import org.hqu.production_ms.domain.authority.SysUserRole;
import org.hqu.production_ms.domain.authority.SysUserRoleExample;
import org.hqu.production_ms.domain.custom.CustomResult;
import org.hqu.production_ms.domain.custom.EUDataGridResult;
import org.hqu.production_ms.domain.po.RolePO;
import org.hqu.production_ms.mapper.authority.SysRoleMapper;
import org.hqu.production_ms.mapper.authority.SysRolePermissionMapper;
import org.hqu.production_ms.mapper.authority.SysUserRoleMapper;
import org.hqu.production_ms.service.RoleService;
import org.hqu.production_ms.util.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	SysRoleMapper sysRoleMapper;
	
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	SysRolePermissionMapper sysRolePermissionMapper;
	
	@Override
	public EUDataGridResult getList(int page, int rows, SysRole sysRole) throws Exception{
		//查询列表
		SysRoleExample example = new SysRoleExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<SysRole> list = sysRoleMapper.selectByExample(example);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<SysRole> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}


	@Override
	public List<SysRole> findByRoleNameAndId(String rolename, String id) throws Exception{
		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria();
		criteria.andRoleNameEqualTo(rolename);
		if(id != null){
			criteria.andRoleIdNotEqualTo(id);
		}
		List<SysRole> sysRoleList = sysRoleMapper.selectByExample(example);
		return sysRoleList;
	}

	
	@Override
	public SysRole get(String string) throws Exception{
		return sysRoleMapper.selectByPrimaryKey(string);
	}


	@Override
	public SysRole findRoleByUserId(String userId) throws Exception{
		SysUserRoleExample example = new SysUserRoleExample();
		SysUserRoleExample.Criteria criteria = example.createCriteria();
		criteria.andSysUserIdEqualTo(userId);
		SysUserRole sysUserRole = sysUserRoleMapper.selectByExample(example).get(0);
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysUserRole.getSysRoleId());
		return sysRole;
	}
	
	@Override
	public CustomResult delete(String string) throws Exception{
		int i = sysRoleMapper.deleteByPrimaryKey(string);
		if(i>0){
			return CustomResult.ok();
		}else{
			return null;
		}
	}

	@Override
	public CustomResult deleteBatch(String[] ids) throws Exception{
		int i = sysRoleMapper.deleteBatch(ids);
		if(i>=0){
			return CustomResult.ok();
		}else{
			return null;
		}
	}

	@Override
	public CustomResult insert(RolePO role) throws Exception{
		//在业务层整合
		SysRolePermission sysRolePermission = new SysRolePermission();
		sysRolePermission.setId(IDUtils.genStringId());
		sysRolePermission.setSysRoleId(role.getRoleId());
		sysRolePermission.setSysPermissionId(role.getPermission());
		//存角色权限表
		int k = sysRolePermissionMapper.insertSelective(sysRolePermission);
		
		int i = sysRoleMapper.insert(role);
		if(i>0 && k>0){
			return CustomResult.ok();
		}else{
			return CustomResult.build(101, "新增角色信息失败");
		}
	}

	@Override
	public CustomResult update(RolePO role) throws Exception{
		int i = sysRoleMapper.updateByPrimaryKeySelective(role);
		if(i>0){
			return CustomResult.ok();
		}else{
			return CustomResult.build(101, "修改角色信息失败");
		}
	}

	@Override
	public CustomResult updateAll(RolePO role) throws Exception{
		//在业务层整合处理
		SysRolePermission sysRolePermission = new SysRolePermission();
		sysRolePermission.setSysRoleId(role.getRoleId());
		sysRolePermission.setSysPermissionId(role.getPermission());
		//修改角色权限表
		int k = sysRolePermissionMapper.updateRolePermission(sysRolePermission);
		
		int i = sysRoleMapper.updateByPrimaryKey(role);
		if(i>0 && k>0){
			return CustomResult.ok();
		}else{
			return CustomResult.build(101, "修改角色信息失败");
		}
	}

	@Override
	public List<SysRole> find() throws Exception{
		SysRoleExample example = new SysRoleExample();
		return sysRoleMapper.selectByExample(example);
	}


	@Override
	public List<SysRole> searchSysRoleBySysRoleName(String sysRoleName) throws Exception{
		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria();
		criteria.andRoleNameLike(sysRoleName);
		return sysRoleMapper.selectByExample(example);
	}
	
	@Override
	public List<SysRole> searchSysRoleBySysRoleId(String sysRoleId) throws Exception{
		SysRoleExample example = new SysRoleExample();
		SysRoleExample.Criteria criteria = example.createCriteria();
		criteria.andRoleIdLike(sysRoleId);
		return sysRoleMapper.selectByExample(example);
	}


	@Override
	public EUDataGridResult searchRoleByRoleId(Integer page, Integer rows,
			String roleId) throws Exception{
		//分页处理
		PageHelper.startPage(page, rows);
		List<SysRole> list = sysRoleMapper.searchRoleByRoleId(roleId);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<SysRole> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}


	@Override
	public EUDataGridResult searchRoleByRoleName(Integer page, Integer rows,
			String roleName) throws Exception{
		//分页处理
		PageHelper.startPage(page, rows);
		List<SysRole> list = sysRoleMapper.searchRoleByRoleName(roleName);
		//创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<SysRole> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

}
