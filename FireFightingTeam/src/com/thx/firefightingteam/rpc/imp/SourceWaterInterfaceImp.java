package com.thx.firefightingteam.rpc.imp;

import java.util.ArrayList;
import java.util.List;

import com.thx.firefightingteam.modle.SourceWater;
import com.thx.firefightingteam.rpc.SourceWaterInterface;

public class SourceWaterInterfaceImp implements SourceWaterInterface{

	@Override
	public List<SourceWater> getSourceWaters() {
		List<SourceWater> sourceWaters=new ArrayList<SourceWater>();
		for(int i=0;i<10;i++){
			SourceWater sw=new SourceWater();
			sw.setId(i);
			sw.setName("Test"+i);
			sw.setStatu(i%2);
		/*	sw.setLat(lat);
			112.555607,37.780613
			sourceWaters*/
		}
		return null;
	}

}
