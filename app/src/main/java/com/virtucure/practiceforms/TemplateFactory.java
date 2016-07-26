package com.virtucure.practiceforms;

import com.virtucare.practiceforms.dto.DentalAssessmentFormDTO;
import com.virtucare.practiceforms.dto.GeneralHealthScreeningFormDTO;
import com.virtucare.practiceforms.dto.HistoryTakingFormDTO;

/**
 * Created by AJITI on 15-Jul-16.
 */
public class TemplateFactory {

    public static Object getTemplate(String formName) {

        if(formName == null || formName.isEmpty()){
            return null;
        }
        else if("dentalassessment".equals(formName)){
            return new DentalAssessmentFormDTO();
        }
        else if("generalhealthscreening".equals(formName)){
            return new GeneralHealthScreeningFormDTO();
        }
        else if("historytaking".equals(formName)){
            return new HistoryTakingFormDTO();
        }
        else if("generalphysicalexamination".equals(formName)){
        }
        else if("dischargesheet".equals(formName)){
        }
        return null;
    }
}
