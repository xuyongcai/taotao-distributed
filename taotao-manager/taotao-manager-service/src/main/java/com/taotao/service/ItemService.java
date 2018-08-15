package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

    TbItem getItemById(Long itemId);

    EUDataGridResult getItemList(int page, int row);

    TaotaoResult createItem(TbItem item,String desc, String itemParam) throws Exception;
}
