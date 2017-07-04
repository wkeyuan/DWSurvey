package com.key.dwsurvey.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.key.dwsurvey.dao.AnRadioDao;
import com.key.dwsurvey.entity.Question;
import com.key.common.QuType;
import com.key.dwsurvey.entity.QuRadio;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.key.common.dao.BaseDaoImpl;
import com.key.dwsurvey.entity.AnRadio;
import com.key.dwsurvey.entity.DataCross;
import com.key.dwsurvey.entity.QuCheckbox;

/**
 * 单选题 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnRadioDaoImpl extends BaseDaoImpl<AnRadio, String> implements AnRadioDao {

	@Override
	public void findGroupStats(Question question) {
		
		String sql="select qu_item_id,count(qu_item_id) count from t_an_radio where visibility=1 and  qu_id=? GROUP BY qu_item_id";
		List<Object[]> list=this.getSession().createSQLQuery(sql).setString(0, question.getId()).list();
		List<QuRadio> quRadios=question.getQuRadios();
		
		int count=0;
		for (QuRadio quRadio : quRadios) {
			String quRadioId=quRadio.getId();
			for (Object[] objects : list) {
				if(quRadioId.equals(objects[0].toString())){
					int anCount=Integer.parseInt(objects[1].toString());
					count+=anCount;
					quRadio.setAnCount(anCount);
					continue;
				}
			}
		}
		question.setAnCount(count);
	}

	@Override
	public List<DataCross> findStatsDataCross(Question rowQuestion,
                                              Question colQuestion) {
		List<DataCross> dataCrosses=new ArrayList<DataCross>();
		List<QuRadio> rowList=rowQuestion.getQuRadios();
		
		Session session=this.getSession();
		
		String rowTab=" t_an_radio t1 ";
		String colTab="";
		String groupSql="";
		String columnSql="";
		String whereSql=" where t1.qu_id=? "+ 
							" and t2.qu_id=? "+ 
							" and t1.belong_answer_id=t2.belong_answer_id GROUP BY ";
		String sql="";
		QuType colQuType=colQuestion.getQuType();
		
		if(colQuType==QuType.YESNO){//是非题与是非题
			colTab=" t_an_yesno t2 ";
			groupSql=" t1.qu_item_id,t2.yesno_answer ";
			sql="select "+groupSql+",count(*) from "+rowTab+","+colTab+whereSql+groupSql;
			
			List<Object[]> objects=session.createSQLQuery(sql).setParameter(0, rowQuestion.getId()).setParameter(1, colQuestion.getId()).list();
			
			List<String> colList=new ArrayList<String>();
			colList.add(colQuestion.getYesnoOption().getTrueValue());
			colList.add(colQuestion.getYesnoOption().getFalseValue());
		
			for (QuRadio quRadio : rowList) {
				DataCross rowDataCross=new DataCross();
				String rowName=quRadio.getOptionName();
				String rowQuItemId=quRadio.getId();
				rowDataCross.setOptionName(rowName);
				List<DataCross> colDataCrosses=rowDataCross.getColDataCrosss();
					for (String col : colList) {
						DataCross colDataCross=new DataCross();
						colDataCross.setOptionName(col);
							for (Object[] objs : objects) {
								
								String anRowQuItemId=objs[0].toString();
								String colYesno_answer=objs[1].toString();
								int objCount=Integer.parseInt(objs[2].toString());
								
								if(rowQuItemId.equals(anRowQuItemId) && col.equals(colYesno_answer)){
									colDataCross.setCount(objCount);
									break;
								}
							}
							colDataCrosses.add(colDataCross);
					}
					dataCrosses.add(rowDataCross);
			}
			
		}else if(colQuType==QuType.RADIO  || colQuType==QuType.COMPRADIO){//是非题与单选题
			colTab=" t_an_radio t2 ";
			columnSql=" t1.qu_item_id as quItemId1, t2.qu_item_id as quItemId2 ";
			groupSql=" t1.qu_item_id,t2.qu_item_id ";
			sql="select "+columnSql+",count(*) from "+rowTab+","+colTab+whereSql+groupSql;

			List<Object[]> objects=session.createSQLQuery(sql).setParameter(0, rowQuestion.getId()).setParameter(1, colQuestion.getId()).list();
			
			List<QuRadio> quRadios=colQuestion.getQuRadios();
		
			for (QuRadio rowQuRadio : rowList) {
				DataCross rowDataCross=new DataCross();
				String rowName=rowQuRadio.getOptionName();
				String rowQuItemId=rowQuRadio.getId();
				rowDataCross.setOptionName(rowName);
				List<DataCross> colDataCrosses=rowDataCross.getColDataCrosss();
					for (QuRadio quRadio : quRadios) {
						DataCross colDataCross=new DataCross();
						colDataCross.setOptionName(quRadio.getOptionName());
						String quRadioId=quRadio.getId();
						
							for (Object[] objs : objects) {
								String anRowQuItemId=objs[0].toString();
								String anColQuItemId=objs[1].toString();
								int objCount=Integer.parseInt(objs[2].toString());
								
								if(rowQuItemId.equals(anRowQuItemId) && quRadioId.equals(anColQuItemId)){
									colDataCross.setCount(objCount);
									break;
								}
							}
							colDataCrosses.add(colDataCross);
					}
					dataCrosses.add(rowDataCross);
			}
			
		}else if(colQuType==QuType.CHECKBOX || colQuType==QuType.COMPCHECKBOX){//是非题与多选题
			colTab=" t_an_checkbox t2 ";
			columnSql=" t1.qu_item_id as quItemId1, t2.qu_item_id as quItemId2 ";
			groupSql=" t1.qu_item_id, t2.qu_item_id ";
			sql="select "+columnSql+",count(*) from "+rowTab+","+colTab+whereSql+groupSql;

			List<Object[]> objects=session.createSQLQuery(sql).setParameter(0, rowQuestion.getId()).setParameter(1, colQuestion.getId()).list();
			
			List<QuCheckbox> quCheckboxs=colQuestion.getQuCheckboxs();
			
			for (QuRadio rowQuRadio : rowList) {
				DataCross rowDataCross=new DataCross();
				String rowName=rowQuRadio.getOptionName();
				String rowQuItemId=rowQuRadio.getId();
				rowDataCross.setOptionName(rowName);
				List<DataCross> colDataCrosses=rowDataCross.getColDataCrosss();
				
				for (QuCheckbox quCheckbox : quCheckboxs) {
						DataCross colDataCross=new DataCross();
						colDataCross.setOptionName(quCheckbox.getOptionName());
						String colQuCheckboxId=quCheckbox.getId();
							for (Object[] objs : objects) {
								
								String anRowQuItemId=objs[0].toString();
								String anColQuItemId=objs[1].toString();
								
								int objCount=Integer.parseInt(objs[2].toString());
								if(rowQuItemId.equals(anRowQuItemId) && colQuCheckboxId.equals(anColQuItemId)){
									colDataCross.setCount(objCount);
									break;
								}
							}
							colDataCrosses.add(colDataCross);
					}
					dataCrosses.add(rowDataCross);
			}
		}
		return dataCrosses;
	}
	
	@Override
	public List<DataCross> findStatsDataChart(Question question) {
		List<DataCross> crosses=new ArrayList<DataCross>();
		String sql="select qu_item_id,count(*) from t_an_radio where qu_id=? GROUP BY qu_item_id";
		
		String quId=question.getId();
		List<Object[]> list=this.getSession().createSQLQuery(sql).setParameter(0, quId).list();
		
		List<QuRadio> quRadios=question.getQuRadios();
		for (QuRadio quRadio : quRadios) {
			String quItemId=quRadio.getId();
			String optionName=quRadio.getOptionName();
			DataCross dataCross=new DataCross();
			dataCross.setOptionName(optionName);
			for (Object[] objects : list) {
				String anQuItemId=objects[0].toString();
				int count=Integer.parseInt(objects[1].toString());
				if(quItemId.equals(anQuItemId)){
					dataCross.setCount(count);		
					break;
				}
			}
			crosses.add(dataCross);
		}
		return crosses;
	}

}
