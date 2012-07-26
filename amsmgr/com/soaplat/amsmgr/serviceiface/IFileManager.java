package com.soaplat.amsmgr.serviceiface;

import java.util.List;

public interface IFileManager {

	public abstract Object deleteFiles(List<String> progListIds,
			String storageClassCode, String inputManId);

}