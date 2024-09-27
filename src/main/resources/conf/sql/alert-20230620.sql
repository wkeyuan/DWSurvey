ALTER TABLE `dwsurvey`.`t_survey_json`
    CHANGE COLUMN `survey_json_text` `survey_json_text` MEDIUMTEXT NULL DEFAULT NULL;

ALTER TABLE `dwsurvey`.`t_survey_directory`
    CHANGE COLUMN `survey_type` `survey_type` VARCHAR(55) NULL DEFAULT NULL ;

ALTER TABLE `dwsurvey`.`t_cascade_json`
    CHANGE COLUMN `json_data` `json_data` JSON NULL DEFAULT NULL ;
ALTER TABLE `dwsurvey`.`t_survey_json`
    CHANGE COLUMN `survey_json_text` `survey_json_text` MEDIUMTEXT NULL DEFAULT NULL;

ALTER TABLE `t_survey_directory`.`t_survey_json` CHANGE COLUMN `survey_type` `survey_type` VARCHAR(55) NULL DEFAULT NULL;
ALTER TABLE `dwsurvey`.`t_survey_json`  ADD INDEX `surveyId` (`survey_id` ASC);
ALTER TABLE `dwsurvey`.`t_survey_json`  ADD INDEX `sid` (`sid` ASC);
