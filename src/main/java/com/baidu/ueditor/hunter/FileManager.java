package com.baidu.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.aliyun.oss.model.OSSObjectSummary;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MultiState;
import com.baidu.ueditor.define.State;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.key.common.plugs.aliyun.AliyunOSS;
import com.key.common.plugs.baiduyun.BaiduBOS;
import com.key.common.utils.DiaowenProperty;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
//		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.dir = (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		
	}
	
	public State listFile ( int index ) {
		
		if("local".equals(DiaowenProperty.DWSTORAGETYPE)) {
			File dir = new File( this.rootPath + this.dir );
			
			System.out.println("dir:"+this.rootPath + this.dir);
			State state = null;

			if ( !dir.exists() ) {
				return new BaseState( false, AppInfo.NOT_EXIST );
			}
			
			if ( !dir.isDirectory() ) {
				return new BaseState( false, AppInfo.NOT_DIRECTORY );
			}
			
			Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
			
			for (File file : list) {
				System.out.println(file.getName());
			}
			if ( index < 0 || index > list.size() ) {
				state = new MultiState( true );
			} else {
				Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
				state = this.getState( fileList );
			}
			
			state.putInfo( "start", index );
			state.putInfo( "total", list.size() );
			
			return state;
		}else{
			
			return listFileByYun(index);
			
		}
		
	}
	
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	

/*	private String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
		
		return path.replace( this.rootPath, "/" );
		
	}
*/

	private String getPath ( File file ) {
//		String path = file.getAbsolutePath();
		//后来自己回的
		String path=PathFormat.format(file.getAbsolutePath());
		return path.replace( this.rootPath, "/" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
	/*
	 * yun实现的操作
	 */
	public State listFileByYun ( int index ) {
		dir=dir.substring(1);
		
		//得到当前用户
		String userId=PathFormat.getUserId();
		dir=dir+userId+"/";
		State state = null;
		try{
			Collection list = null;
			if("aliyunOSS".equals(DiaowenProperty.DWSTORAGETYPE)){
				// 阿里云支持 
				list = AliyunOSS.getObjectList(DiaowenProperty.UPLOADFILE_BACKET, dir,index+count,count);
			}else if("baiduBOS".equals(DiaowenProperty.DWSTORAGETYPE)){
				list = BaiduBOS.getObjectList(DiaowenProperty.UPLOADFILE_BACKET, dir,index+count,count);
				
			}

			if ( index < 0 || index > list.size() ) {
				state = new MultiState( true );
			} else {
				Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
				state = this.getStateByYun( fileList );
			}
			
			state.putInfo( "start", index );
			state.putInfo( "total", list.size() );
			
		}catch(Exception e){
			state = new BaseState( false, AppInfo.NOT_EXIST );
			e.printStackTrace();
		}
		
		return state;
		
	}
	
	private State getStateByYun ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			
			if(obj instanceof OSSObjectSummary ){
				OSSObjectSummary file = (OSSObjectSummary)obj;

				fileState = new BaseState( true );
//				fileState.putInfo( "url", "http://file.diaowen.net/"+PathFormat.format( file.getKey() ) );
				fileState.putInfo( "url", PathFormat.format( file.getKey() ) );
				state.addState( fileState );
				
			}else{
				//obj instanceof BosObjectSummary 
				BosObjectSummary file = (BosObjectSummary)obj;
				
				fileState = new BaseState( true );
//				fileState.putInfo( "url", "http://file.diaowen.net/"+PathFormat.format( file.getKey() ) );
				fileState.putInfo( "url", PathFormat.format( file.getKey() ) );
				state.addState( fileState );
			}
			
		}
		
		return state;
		
	}
	
	
}
