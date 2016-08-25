package com_t.macvision.mv_078.model.DeviceModle;

import org.w3c.dom.Node;

import com_t.macvision.mv_078.model.DeviceModle.FileBrowserModel.ModelException;

public abstract class FileBrowserNode{

	protected FileBrowserNode(Node node) throws ModelException {
		if (node != null)
			parseNode(node) ;
	}
	
	

	protected abstract void parseNode(Node node) throws ModelException, FileBrowserModel.ModelException;
}