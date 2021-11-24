package net.diaowen.dwsurvey.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.diaowen.dwsurvey.dao.AnYesnoDao;
import net.diaowen.dwsurvey.entity.Question;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import net.diaowen.common.dao.BaseDaoImpl;
import net.diaowen.common.QuType;
import net.diaowen.dwsurvey.entity.AnYesno;
import net.diaowen.dwsurvey.entity.DataCross;
import net.diaowen.dwsurvey.entity.QuCheckbox;
import net.diaowen.dwsurvey.entity.QuRadio;

/**
 * 是非题 dao
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

@Repository
public class AnYesnoDaoImpl extends BaseDaoImpl<AnYesno, String> implements AnYesnoDao {

	@Override
	public void findGroupStats(Question question) {

		String sql="select yesno_answer,count(yesno_answer) from t_an_yesno where  visibility=1 and  qu_id=? GROUP BY yesno_answer";
		List<Object[]> list=this.getSession().createSQLQuery(sql).setParameter(1,question.getId()).list();

		String tranValue = question.getYesnoOption().getTrueValue();
		String falseValue = question.getYesnoOption().getFalseValue();

		question.setParamInt01(0);
		question.setParamInt02(0);
		int count=0;
		for (Object[] objects : list) {
			if(tranValue.equals(objects[0].toString())){
//				quRadio.setAnCount(Integer.parseInt(objects[1].toString()));
				int anCount=Integer.parseInt(objects[1].toString());
				count+=anCount;
				question.setParamInt01(anCount);
				continue;
			}else if(falseValue.equals(objects[0].toString())){
				int anCount=Integer.parseInt(objects[1].toString());
				count+=anCount;
				question.setParamInt02(anCount);
				continue;
			}
		}
		question.setAnCount(count);

	}

	@Override
	public List<DataCross> findStatsDataCross(Question rowQuestion, Question colQuestion) {
		List<DataCross> dataCrosses=new ArrayList<DataCross>();
		List<String> rowList=new ArrayList<String>();
		rowList.add(rowQuestion.getYesnoOption().getTrueValue());
		rowList.add(rowQuestion.getYesnoOption().getFalseValue());

		Session session=this.getSession();

//		select yesno_answer,qu_item_id,count(*) from t_an_yesno t1, t_an_radio t2"+
//		"where t1.qu_id='402880e63dd92431013dd9297ecc0000'"+
//		"and t2.qu_id='402881c83dbff250013dbff6b2d50000'"+
//		"and t1.belong_answer_id=t2.belong_answer_id GROUP BY yesno_answer,qu_item_id

		String rowTab=" t_an_yesno t1 ";
		String colTab="";
		String groupSql="";
		String whereSql=" where t1.qu_id=? "+
							" and t2.qu_id=? "+
							" and t1.belong_answer_id=t2.belong_answer_id GROUP BY ";
		String sql="";
		QuType colQuType=colQuestion.getQuType();

		int[] colCounts=new int[0];

		if(colQuType==QuType.YESNO){//是非题与是非题
			colTab=" t_an_yesno t2 ";
			groupSql=" t1.yesno_answer,t2.yesno_answer ";
			sql="select "+groupSql+",count(*) from "+rowTab+","+colTab+whereSql+groupSql;

			List<Object[]> objects=session.createSQLQuery(sql).setParameter(1, rowQuestion.getId()).setParameter(1, colQuestion.getId()).list();

			List<String> colList=new ArrayList<String>();
			colList.add(colQuestion.getYesnoOption().getTrueValue());
			colList.add(colQuestion.getYesnoOption().getFalseValue());

			colCounts=new int[colList.size()];
			for (String row : rowList) {
				DataCross rowDataCross=new DataCross();
				rowDataCross.setOptionName(row);
				List<DataCross> colDataCrosses=rowDataCross.getColDataCrosss();
//				int rowCount=0;
//				int colIndex=0;
					for (String col : colList) {
						DataCross colDataCross=new DataCross();
						colDataCross.setOptionName(col);
							for (Object[] objs : objects) {

								String rowYesno_answer=objs[0].toString();
								String colYesno_answer=objs[1].toString();
								int objCount=Integer.parseInt(objs[2].toString());

								if(row.equals(rowYesno_answer) && col.equals(colYesno_answer)){
									colDataCross.setCount(objCount);
//									rowCount+=objCount;
//									colCounts[colIndex]+=objCount;
									break;
								}
							}
							colDataCrosses.add(colDataCross);
//							colIndex++;
					}
//					rowDataCross.setRowCount(rowCount);
					dataCrosses.add(rowDataCross);
			}

		}else if(colQuType==QuType.RADIO  || colQuType==QuType.COMPRADIO){//是非题与单选题
			colTab=" t_an_radio t2 ";
			groupSql=" t1.yesno_answer,t2.qu_item_id ";
			sql="select "+groupSql+",count(*) from "+rowTab+","+colTab+whereSql+groupSql;

			List<Object[]> objects=session.createSQLQuery(sql).setParameter(1, rowQuestion.getId()).setParameter(1, colQuestion.getId()).list();

			List<QuRadio> quRadios=colQuestion.getQuRadios();

			colCounts=new int[quRadios.size()];
			for (String row : rowList) {
				DataCross rowDataCross=new DataCross();
				rowDataCross.setOptionName(row);
				List<DataCross> colDataCrosses=rowDataCross.getColDataCrosss();
//				int rowCount=0;
//				int colIndex=0;
					for (QuRadio quRadio : quRadios) {
						DataCross colDataCross=new DataCross();
						colDataCross.setOptionName(quRadio.getOptionName());
						String quRadioId=quRadio.getId();

//						int colCount=0;
							for (Object[] objs : objects) {
								String yesno_answer=objs[0].toString();
								String quItemId=objs[1].toString();
								int objCount=Integer.parseInt(objs[2].toString());

								if(row.equals(yesno_answer) && quRadioId.equals(quItemId)){
									colDataCross.setCount(objCount);
//									rowCount+=objCount;
//									colCounts[colIndex]+=objCount;
									break;
								}
							}
//							colDataCross.setColCount(colCount);
							colDataCrosses.add(colDataCross);
//							colIndex++;
					}
//					rowDataCross.setRowCount(rowCount);
					dataCrosses.add(rowDataCross);
			}

		}else if(colQuType==QuType.CHECKBOX || colQuType==QuType.COMPCHECKBOX){//是非题与多选题
			colTab=" t_an_checkbox t2 ";
			groupSql=" t1.yesno_answer,t2.qu_item_id ";
			sql="select "+groupSql+",count(*) from "+rowTab+","+colTab+whereSql+groupSql;

			List<Object[]> objects=session.createSQLQuery(sql).setParameter(1, rowQuestion.getId()).setParameter(1, colQuestion.getId()).list();

			List<QuCheckbox> quCheckboxs=colQuestion.getQuCheckboxs();
			colCounts=new int[quCheckboxs.size()];

			for (String row : rowList) {
				DataCross rowDataCross=new DataCross();
				rowDataCross.setOptionName(row);
				List<DataCross> colDataCrosses=rowDataCross.getColDataCrosss();
//				int rowCount=0;
//				int colIndex=0;
					for (QuCheckbox quCheckbox : quCheckboxs) {
						DataCross colDataCross=new DataCross();
						colDataCross.setOptionName(quCheckbox.getOptionName());
						String quCheckboxId=quCheckbox.getId();
						int colCount=0;
							for (Object[] objs : objects) {
								String yesno_answer=objs[0].toString();
								String quItemId=objs[1].toString();
								int objCount=Integer.parseInt(objs[2].toString());

								if(row.equals(yesno_answer) && quCheckboxId.equals(quItemId)){
									colDataCross.setCount(objCount);
//									rowCount+=objCount;
//									colCounts[colIndex]+=objCount;
									break;
								}
							}
//							colDataCross.setColCount(colCount);
							colDataCrosses.add(colDataCross);
//							colIndex++;
					}
//					rowDataCross.setRowCount(rowCount);
					dataCrosses.add(rowDataCross);
			}

		}

//		for (DataCross dataCross : dataCrosses) {
//			List<DataCross> crosses=dataCross.getColDataCrosss();
//			int crossIndex=0;
//			for (DataCross dataCross2 : crosses) {
//				dataCross2.setColCount(colCounts[crossIndex++]);
//			}
//		}
		return dataCrosses;
	}

	@Override
	public List<DataCross> findStatsDataChart(Question question) {
		String sql="select yesno_answer,count(*) from t_an_yesno where qu_id=? GROUP BY yesno_answer";

		String quId=question.getId();
		List<Object[]> list=this.getSession().createSQLQuery(sql).setParameter(1, quId).list();
		List<DataCross> crosses=new ArrayList<DataCross>();
		DataCross dataCross0=new DataCross();
		dataCross0.setCount(0);
		dataCross0.setOptionName(question.getYesnoOption().getFalseValue());
		DataCross dataCross1=new DataCross();
		dataCross1.setCount(0);
		dataCross1.setOptionName(question.getYesnoOption().getTrueValue());
		crosses.add(dataCross0);
		crosses.add(dataCross1);

		for (Object[] objects : list) {
			String optionName=objects[0].toString();
			int count=Integer.parseInt(objects[1].toString());
			for (DataCross dataCross : crosses) {
				if(dataCross.getOptionName().equals(optionName)){
					dataCross.setCount(count);
				}
			}
		}
		return crosses;
	}

}
