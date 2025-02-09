/** 升级到5.2.2 建议执行一下以下SQ，更新参数 **/
update t_question set param_int01=0,param_int02=0 where qu_type='2' and param_int01=3 and param_int02=10;