package com.cn.poker.admin.modules.sys.service.impl;

import java.util.List;
import java.util.Map;

import com.cn.poker.admin.common.constant.MsgConstant;
import com.cn.poker.admin.common.entity.Query;
import com.cn.poker.admin.common.entity.R;
import com.cn.poker.admin.common.utils.CommonUtils;
import com.cn.poker.admin.modules.sys.entity.SysMenuEntity;
import com.cn.poker.admin.modules.sys.manager.SysMenuManager;
import com.cn.poker.admin.modules.sys.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 系统菜单
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuManager sysMenuManager;

	@Override
	public R listUserMenu(Long userId) {
		return R.ok().put("menuList", sysMenuManager.listUserMenu(userId));
	}

	@Override
	public List<SysMenuEntity> listMenu(Map<String, Object> params) {
		Query query = new Query(params);
		List<SysMenuEntity> menuList = sysMenuManager.listMenu(query);
		return menuList;
	}

	@Override
	public R listNotButton() {
		List<SysMenuEntity> menuList = sysMenuManager.listNotButton();
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		return CommonUtils.msgNotCheckNull(menuList);
	}

	@Override
	public R saveMenu(SysMenuEntity menu) {
		int count = sysMenuManager.saveMenu(menu);
		return CommonUtils.msg(count);
	}

	@Override
	public R getMenuById(Long id) {
		SysMenuEntity menu = sysMenuManager.getMenuById(id);
		return CommonUtils.msg(menu);
	}

	@Override
	public R updateMenu(SysMenuEntity menu) {
		int count = sysMenuManager.updateMenu(menu);
		return CommonUtils.msg(count);
	}

	@Override
	public R batchRemove(Long[] id) {
		boolean children = sysMenuManager.hasChildren(id);
		if(children) {
			return R.error(MsgConstant.MSG_HAS_CHILD);
		}
		int count = sysMenuManager.batchRemove(id);
		return CommonUtils.msg(id, count);
	}

}
