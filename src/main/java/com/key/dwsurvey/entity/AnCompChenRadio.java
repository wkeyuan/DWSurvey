package com.key.dwsurvey.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.key.common.base.entity.IdEntity;

/**
 * 复合矩阵单选题
 * @author KeYuan
 * @date 2013下午8:49:38
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
@Entity
@Table(name="t_an_comp_chen_radio")
public class AnCompChenRadio extends IdEntity{
		//所属问卷ID
		private String belongId;
		//对应的答卷信息表
		private String belongAnswerId;
		//题目ID
		private String quId;
		//对应的行ID
		private String quRowId;
		//对应的列ID
		private String quColId;
		//对应的结果id 即选项ID
		private String quOptionId;
		
		private Integer visibility=1;
		
		public AnCompChenRadio(){
			
		}
		public AnCompChenRadio(String surveyId, String surveyAnswerId, String quId,String quRowId,String quColId,String quOptionId) {
			this.belongId=surveyId;
			this.belongAnswerId=surveyAnswerId;
			this.quId=quId;
			this.quRowId=quRowId;
			this.quColId=quColId;
			this.quOptionId=quOptionId;
		}
		
		public String getBelongId() {
			return belongId;
		}
		public void setBelongId(String belongId) {
			this.belongId = belongId;
		}
		public String getBelongAnswerId() {
			return belongAnswerId;
		}
		public void setBelongAnswerId(String belongAnswerId) {
			this.belongAnswerId = belongAnswerId;
		}
		public String getQuId() {
			return quId;
		}
		public void setQuId(String quId) {
			this.quId = quId;
		}
		public String getQuRowId() {
			return quRowId;
		}
		public void setQuRowId(String quRowId) {
			this.quRowId = quRowId;
		}
		public String getQuColId() {
			return quColId;
		}
		public void setQuColId(String quColId) {
			this.quColId = quColId;
		}
		public String getQuOptionId() {
			return quOptionId;
		}
		public void setQuOptionId(String quOptionId) {
			this.quOptionId = quOptionId;
		}
		public Integer getVisibility() {
			return visibility;
		}
		public void setVisibility(Integer visibility) {
			this.visibility = visibility;
		}
		
}
