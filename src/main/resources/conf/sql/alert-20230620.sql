ALTER TABLE `dwsurvey`.`t_survey_json`
    CHANGE COLUMN `survey_json_text` `survey_json_text` MEDIUMTEXT NULL DEFAULT NULL;

ALTER TABLE `dwsurvey`.`t_survey_directory`
    CHANGE COLUMN `survey_type` `survey_type` VARCHAR(55) NULL DEFAULT NULL ;
