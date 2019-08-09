package com.wjl.mq.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

/*************************************************************
 * The following Java source code was automatically generated
 * by the TRANSLATE feature in Salford Predictive Modeler(R).
 * Modeling version: 8.2.0.782, Translation version: 8.2.0.782
 *************************************************************/
@Slf4j
@Controller
class TreenetScore {

//    @Autowired
//    private DataModelService dataModelService;

    /**********************************************************
     *     **** APPLICATION-DEPENDENT MISSING VALUES ****
     * The two constants must be set **by you** to whatever
     * value(s) you use in your data management or programming
     * workflow to represent missing data.
     **********************************************************/
    public static double DBL_MISSING_VALUE = -10e36;
    public static int INT_MISSING_VALUE = -999999;

    /************
     * PREDICTORS
     ************/

    public String[] doubleVariables = {"CALL_IN_CNT_RSD", "CALL_IN_TIME_STD",
            "EATCH_CALL_RATE", "PHONE_USER_TIME",
            "YP_CNT", "PHONE2HOUSEPLACE", "CONTACT_NUM"};
    public String[] intVariables = {"DM_TEL_NOUSE_BASEDAYS"};
    public String[] stringVariables = null;

    private double CALL_IN_CNT_RSD, CALL_IN_TIME_STD, EATCH_CALL_RATE,
            PHONE_USER_TIME, YP_CNT, PHONE2HOUSEPLACE, CONTACT_NUM;
    private int DM_TEL_NOUSE_BASEDAYS;

    public TreenetScore() {
    }

    ;

    /* Returns the list of double predictors used by the model */
    public String[] getDoubleVariables() {
        return doubleVariables;
    }

    /* Returns the list of int predictors used by the model */
    public String[] getIntVariables() {
        return intVariables;
    }

    /* Returns the list of string predictors used by the model */
    public String[] getStringVariables() {
        return stringVariables;
    }

    /* Sets values for double predictors */
    public void setDoubleVariables(double[] values) {
        if (values.length != doubleVariables.length) {
            System.out.println("Error! expecting " + doubleVariables.length + " values for double predictors.");
        } else {
            CALL_IN_CNT_RSD = values[0];
            CALL_IN_TIME_STD = values[1];
            EATCH_CALL_RATE = values[2];
            PHONE_USER_TIME = values[3];
            YP_CNT = values[4];
            PHONE2HOUSEPLACE = values[5];
            CONTACT_NUM = values[6];
        }
    }

    /* Sets values for int predictors */
    public void setIntVariables(int[] values) {
        if (values.length != intVariables.length) {
            System.out.println("Error! expecting " + intVariables.length + " values for int predictors.");
        } else {
            DM_TEL_NOUSE_BASEDAYS = values[0];
        }
    }

    /* Sets values for string predictors */
    public void setStringVariables(String[] values) {
    }

    private double init = 0.0;

    /*Sets the optional initialization value*/
  /*Ignored for multinomial classification models*/
    public void setInitValue(double value) {
        init = value;
    }

    /****************************************************************
     * Here come the models in the grove.  A shell for calling them
     * appears at the end of this source file.
     ****************************************************************/

  /* TreeNet version: 8.2.0.782 */
  /* TreeNet: TreeNet_1 */
  /* Timestamp: 20180122190440 */
  /* Grove: C:\Users\189397~1\AppData\Local\Temp\s6qg_00050.grv */
  /* Target: DPD14 */
  /* N trees: 55 */
  /* N target classes: 2 */
    private void impute_missing() {

        /*******************************************************/
    /* The following predictors had no missing data in     */
    /* the learn sample, so the TreeNet model is unable to */
    /* accommodate missing data for them during scoring.   */
    /* They must be imputed.  These particular values are  */
    /* the learn sample medians and/or modes. These are    */
    /* provided as a convenience, you may wish to replace  */
    /* these expressions with your own.                    */
        /*******************************************************/

        if (DM_TEL_NOUSE_BASEDAYS == INT_MISSING_VALUE) DM_TEL_NOUSE_BASEDAYS = 1;
        if (CALL_IN_CNT_RSD == DBL_MISSING_VALUE) CALL_IN_CNT_RSD = 82;
        if (EATCH_CALL_RATE == DBL_MISSING_VALUE) EATCH_CALL_RATE = 0.24;
        if (PHONE_USER_TIME == DBL_MISSING_VALUE) PHONE_USER_TIME = 45;
    }

    /* The following are used to store the original values of predictors
    subject to imputation */
    double saved[] = new double[3];
    int savei[] = new int[1];

    private void savePreds() {
        saved[0] = CALL_IN_CNT_RSD;
        saved[1] = EATCH_CALL_RATE;
        saved[2] = PHONE_USER_TIME;
        savei[0] = DM_TEL_NOUSE_BASEDAYS;
    }

    private void restorePreds() {
        CALL_IN_CNT_RSD = saved[0];
        EATCH_CALL_RATE = saved[1];
        PHONE_USER_TIME = saved[2];
        DM_TEL_NOUSE_BASEDAYS = savei[0];
    }

    tree_1 tree_1_obj = new tree_1();
    tree_2 tree_2_obj = new tree_2();
    tree_3 tree_3_obj = new tree_3();
    tree_4 tree_4_obj = new tree_4();
    tree_5 tree_5_obj = new tree_5();
    tree_6 tree_6_obj = new tree_6();
    tree_7 tree_7_obj = new tree_7();
    tree_8 tree_8_obj = new tree_8();
    tree_9 tree_9_obj = new tree_9();
    tree_10 tree_10_obj = new tree_10();
    tree_11 tree_11_obj = new tree_11();
    tree_12 tree_12_obj = new tree_12();
    tree_13 tree_13_obj = new tree_13();
    tree_14 tree_14_obj = new tree_14();
    tree_15 tree_15_obj = new tree_15();
    tree_16 tree_16_obj = new tree_16();
    tree_17 tree_17_obj = new tree_17();
    tree_18 tree_18_obj = new tree_18();
    tree_19 tree_19_obj = new tree_19();
    tree_20 tree_20_obj = new tree_20();
    tree_21 tree_21_obj = new tree_21();
    tree_22 tree_22_obj = new tree_22();
    tree_23 tree_23_obj = new tree_23();
    tree_24 tree_24_obj = new tree_24();
    tree_25 tree_25_obj = new tree_25();
    tree_26 tree_26_obj = new tree_26();
    tree_27 tree_27_obj = new tree_27();
    tree_28 tree_28_obj = new tree_28();
    tree_29 tree_29_obj = new tree_29();
    tree_30 tree_30_obj = new tree_30();
    tree_31 tree_31_obj = new tree_31();
    tree_32 tree_32_obj = new tree_32();
    tree_33 tree_33_obj = new tree_33();
    tree_34 tree_34_obj = new tree_34();
    tree_35 tree_35_obj = new tree_35();
    tree_36 tree_36_obj = new tree_36();
    tree_37 tree_37_obj = new tree_37();
    tree_38 tree_38_obj = new tree_38();
    tree_39 tree_39_obj = new tree_39();
    tree_40 tree_40_obj = new tree_40();
    tree_41 tree_41_obj = new tree_41();
    tree_42 tree_42_obj = new tree_42();
    tree_43 tree_43_obj = new tree_43();
    tree_44 tree_44_obj = new tree_44();
    tree_45 tree_45_obj = new tree_45();
    tree_46 tree_46_obj = new tree_46();
    tree_47 tree_47_obj = new tree_47();
    tree_48 tree_48_obj = new tree_48();
    tree_49 tree_49_obj = new tree_49();
    tree_50 tree_50_obj = new tree_50();
    tree_51 tree_51_obj = new tree_51();
    tree_52 tree_52_obj = new tree_52();
    tree_53 tree_53_obj = new tree_53();
    tree_54 tree_54_obj = new tree_54();
    tree_55 tree_55_obj = new tree_55();

    public double TreeNet_1_1() {

        double response = 0.0;

        response += init;
        response += tree_1_obj.score();
        response += tree_2_obj.score();
        response += tree_3_obj.score();
        response += tree_4_obj.score();
        response += tree_5_obj.score();
        response += tree_6_obj.score();
        response += tree_7_obj.score();
        response += tree_8_obj.score();
        response += tree_9_obj.score();
        response += tree_10_obj.score();
        response += tree_11_obj.score();
        response += tree_12_obj.score();
        response += tree_13_obj.score();
        response += tree_14_obj.score();
        response += tree_15_obj.score();
        response += tree_16_obj.score();
        response += tree_17_obj.score();
        response += tree_18_obj.score();
        response += tree_19_obj.score();
        response += tree_20_obj.score();
        response += tree_21_obj.score();
        response += tree_22_obj.score();
        response += tree_23_obj.score();
        response += tree_24_obj.score();
        response += tree_25_obj.score();
        response += tree_26_obj.score();
        response += tree_27_obj.score();
        response += tree_28_obj.score();
        response += tree_29_obj.score();
        response += tree_30_obj.score();
        response += tree_31_obj.score();
        response += tree_32_obj.score();
        response += tree_33_obj.score();
        response += tree_34_obj.score();
        response += tree_35_obj.score();
        response += tree_36_obj.score();
        response += tree_37_obj.score();
        response += tree_38_obj.score();
        response += tree_39_obj.score();
        response += tree_40_obj.score();
        response += tree_41_obj.score();
        response += tree_42_obj.score();
        response += tree_43_obj.score();
        response += tree_44_obj.score();
        response += tree_45_obj.score();
        response += tree_46_obj.score();
        response += tree_47_obj.score();
        response += tree_48_obj.score();
        response += tree_49_obj.score();
        response += tree_50_obj.score();
        response += tree_51_obj.score();
        response += tree_52_obj.score();
        response += tree_53_obj.score();
        response += tree_54_obj.score();
        response += tree_55_obj.score();

        return response;
    }

    public double[] getScore() {
        savePreds();
        impute_missing();

        /********************************/
    /* Class-specific probabilities */
        /********************************/

        double e2y;
        double[] prob = {0.0, 0.0};

        e2y = Math.exp(2.0 * TreeNet_1_1());
        prob[1] = e2y / (1.0 + e2y); /* Target class: DPD14 = 1 */
        prob[0] = 1.0 - prob[1]; /* Target class: DPD14 = 0 */
        restorePreds();

        return prob;
    }

    public int[] targetClass() {
        int[] value = {0, 1};
        return value;
    }

    private class tree_1 {

        /* Tree 1 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 31.5) node = 2;
                    else node = 4;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -3;
                    break;

                case -3:
                    target = -0.48420541;
                    node = 3;
                    done = true;
                    break;

                case 3:
                    if (YP_CNT != DBL_MISSING_VALUE) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.69078676;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.71675065;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 100.720001221) node = -4;
                    else node = 5;
                    break;

                case -4:
                    target = -0.73011601;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 6;
                    else node = -7;
                    break;

                case -7:
                    target = -0.57668215;
                    node = 7;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM < 46.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = -0.67427651;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -0.71675323;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_2 {

        /* Tree 2 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 32.5) node = 2;
                    else node = 5;
                    break;

                case 2:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 3;
                    else node = -4;
                    break;

                case -4:
                    target = -0.00039597315;
                    node = 4;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 4;
                    else node = -3;
                    break;

                case -3:
                    target = -0.061609119;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 36.1149978638) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0057337971;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.032896499;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 6;
                    else node = -8;
                    break;

                case -8:
                    target = -0.021774253;
                    node = 8;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 7;
                    else node = -7;
                    break;

                case -7:
                    target = 0.12108471;
                    node = 7;
                    done = true;
                    break;

                case 7:
                    if (CONTACT_NUM < 55.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.019103178;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -0.0038605241;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_3 {

        /* Tree 3 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 2;
                    else node = 6;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -5;
                    break;

                case -5:
                    target = 0.09985999;
                    node = 5;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 49.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 68.0699996948) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.010864728;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.040084726;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 98.6949996948) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.0012188092;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.020079691;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 31.5) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = -0.0011319917;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = -0.01865254;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_4 {

        /* Tree 4 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 44.5) node = 2;
                    else node = 5;
                    break;

                case 2:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 3;
                    else node = -4;
                    break;

                case -4:
                    target = -0.0058011294;
                    node = 4;
                    done = true;
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.254999995232) node = 4;
                    else node = -3;
                    break;

                case -3:
                    target = 0.027701644;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (YP_CNT < 2.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0040907371;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.044599477;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 75.1949996948) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = -0.014044178;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0034018296;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_5 {

        /* Tree 5 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 8 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -1;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 2;
                    else node = 2;          /* default direction */
                    break;

                case -1:
                    target = -0.012959902;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 3;
                    else node = -8;
                    break;

                case -8:
                    target = -0.063476212;
                    node = 8;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 43.5849990845) node = -2;
                    else node = 4;
                    break;

                case -2:
                    target = -0.0039161358;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 63.5) node = 5;
                    else node = -7;
                    break;

                case -7:
                    target = -0.0037558447;
                    node = 7;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 6;
                    else node = -6;
                    break;

                case -6:
                    target = 0.11406141;
                    node = 6;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM < 19.5) node = -3;
                    else node = 7;
                    break;

                case -3:
                    target = 0.053408013;
                    node = 3;
                    done = true;
                    break;

                case 7:
                    if (CALL_IN_CNT_RSD < 199.554992676) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.010349351;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.033494221;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_6 {

        /* Tree 6 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = 0.094818592;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM < 60.5) node = 3;
                    else node = 5;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 75.9649963379) node = 4;
                    else node = -3;
                    break;

                case -3:
                    target = 0.026647988;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (EATCH_CALL_RATE < 0.254999995232) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.0028198782;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.026331305;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.185000002384) node = -4;
                    else node = 6;
                    break;

                case -4:
                    target = -0.015237766;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -5;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = -6;
                    else node = -6;          /* default direction */
                    break;

                case -5:
                    target = -0.013351807;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0045239469;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_7 {

        /* Tree 7 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -6;
                    break;

                case -6:
                    target = 0.1008424;
                    node = 6;
                    done = true;
                    break;

                case 2:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 3;
                    else node = 5;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 33.6849975586) node = -1;
                    else node = 4;
                    break;

                case -1:
                    target = -0.003316622;
                    node = 1;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM < 169.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.017984251;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.0002686582;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.305000007153) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.013033666;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.0079418441;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_8 {

        /* Tree 8 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = 0.077434692;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM < 30.5) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = 0.01924844;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 18.5) node = 4;
                    else node = -6;
                    break;

                case -6:
                    target = -0.0022526848;
                    node = 6;
                    done = true;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 7.5) node = 5;
                    else node = -5;
                    break;

                case -5:
                    target = 0.021575382;
                    node = 5;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 658) node = 6;
                    else node = -4;
                    break;

                case -4:
                    target = 0.13369815;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 4.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.046980197;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = -0.009475255;
                    node = 3;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_9 {

        /* Tree 9 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (EATCH_CALL_RATE < 0.155000001192) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.010273765;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -7;
                    break;

                case -7:
                    target = 0.067529555;
                    node = 7;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 46.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 34.3950004578) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.0025268664;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.022526813;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.425000011921) node = 6;
                    else node = -6;
                    break;

                case -6:
                    target = 0.028675394;
                    node = 6;
                    done = true;
                    break;

                case 6:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -4;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = -5;
                    else node = -5;          /* default direction */
                    break;

                case -4:
                    target = -0.01027423;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.0040989421;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_10 {

        /* Tree 10 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 148.205001831) node = 2;
                    else node = 4;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -3;
                    break;

                case -3:
                    target = 0.083233118;
                    node = 3;
                    done = true;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 38.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0040421589;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0068659245;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -7;
                    break;

                case -7:
                    target = 0.06885159;
                    node = 7;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 42.5) node = -4;
                    else node = 6;
                    break;

                case -4:
                    target = 0.043987919;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_TIME_STD < 122.375) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.0001847236;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.021938134;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_11 {

        /* Tree 11 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 42.5) node = 2;
                    else node = 5;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD < 48.2949981689) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = 0.00030293544;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = 4;
                    else node = -4;
                    break;

                case -4:
                    target = 0.054774423;
                    node = 4;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 307.875) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.0095017245;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.041297641;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.215000003576) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = -0.011242645;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0011139391;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_12 {

        /* Tree 12 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 24.5) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = 0.008817839;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 59.2399978638) node = -2;
                    else node = 3;
                    break;

                case -2:
                    target = -0.010506661;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 4;
                    else node = -7;
                    break;

                case -7:
                    target = 0.026508356;
                    node = 7;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM < 31.5) node = 5;
                    else node = -6;
                    break;

                case -6:
                    target = -0.00061717958;
                    node = 6;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 106.38999939) node = -3;
                    else node = 6;
                    break;

                case -3:
                    target = 0.0087961393;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 134.75) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.0061852177;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.083827278;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_13 {

        /* Tree 13 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -8;
                    break;

                case -8:
                    target = 0.035570441;
                    node = 8;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM < 65.5) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 68.9499969482) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.00052111639;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.014698372;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 144.824996948) node = 5;
                    else node = -7;
                    break;

                case -7:
                    target = 0.010598775;
                    node = 7;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.324999988079) node = 6;
                    else node = -6;
                    break;

                case -6:
                    target = 0.0059002136;
                    node = 6;
                    done = true;
                    break;

                case 6:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 7;
                    else node = -5;
                    break;

                case -5:
                    target = -0.012693305;
                    node = 5;
                    done = true;
                    break;

                case 7:
                    if (YP_CNT < 1.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = -0.0061338336;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.015344953;
                    node = 4;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_14 {

        /* Tree 14 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 42.5) node = 2;
                    else node = 6;
                    break;

                case 2:
                    if (EATCH_CALL_RATE < 0.254999995232) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.00052097702;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.080455831;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -5;
                    break;

                case -5:
                    target = 0.032539386;
                    node = 5;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 16.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.070639838;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.0095037312;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 7;
                    else node = -8;
                    break;

                case -8:
                    target = -0.0056453041;
                    node = 8;
                    done = true;
                    break;

                case 7:
                    if (CONTACT_NUM < 215.5) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = -0.00089426274;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = -0.013570254;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_15 {

        /* Tree 15 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 9, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 2;
                    else node = -9;
                    break;

                case -9:
                    target = -0.0060436143;
                    node = 9;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 3;
                    else node = -8;
                    break;

                case -8:
                    target = -0.0606158;
                    node = 8;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 111.845001221) node = 4;
                    else node = 6;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -3;
                    break;

                case -3:
                    target = 0.039013464;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 278.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0042949246;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.014886449;
                    node = 2;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 7;
                    else node = -7;
                    break;

                case -7:
                    target = 0.02293677;
                    node = 7;
                    done = true;
                    break;

                case 7:
                    if (CONTACT_NUM < 509.5) node = 8;
                    else node = -6;
                    break;

                case -6:
                    target = -0.027591451;
                    node = 6;
                    done = true;
                    break;

                case 8:
                    if (CALL_IN_CNT_RSD < 125.619995117) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.0044491273;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.032717244;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_16 {

        /* Tree 16 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = -0.0057931868;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (EATCH_CALL_RATE < 0.335000008345) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (YP_CNT < 3.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0035401954;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.027577699;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (EATCH_CALL_RATE < 0.465000003576) node = 5;
                    else node = -6;
                    break;

                case -6:
                    target = 0.080698577;
                    node = 6;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 6;
                    else node = -5;
                    break;

                case -5:
                    target = 0.092801757;
                    node = 5;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM < 28.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.064121316;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.016503856;
                    node = 4;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_17 {

        /* Tree 17 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 70.0449981689) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.0048765474;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = 3;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 4;
                    else node = 4;          /* default direction */
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.47499999404) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = -0.0077684467;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.10550514;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 102.5) node = 5;
                    else node = -7;
                    break;

                case -7:
                    target = -0.009506561;
                    node = 7;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 6;
                    else node = -6;
                    break;

                case -6:
                    target = 0.038325284;
                    node = 6;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM < 29.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.023299597;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.0064283588;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_18 {

        /* Tree 18 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (EATCH_CALL_RATE < 0.164999991655) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.0093925955;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -7;
                    break;

                case -7:
                    target = 0.016955127;
                    node = 7;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 109.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (YP_CNT != DBL_MISSING_VALUE) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.012888764;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = -0.0033099999;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -4;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 6;
                    else node = 6;          /* default direction */
                    break;

                case -4:
                    target = -0.014069709;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_TIME_STD < 78.375) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = -0.0045746755;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0094382016;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_19 {

        /* Tree 19 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = 0.02881313;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (EATCH_CALL_RATE < 0.305000007153) node = 3;
                    else node = 6;
                    break;

                case 3:
                    if (CONTACT_NUM < 97.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (EATCH_CALL_RATE < 0.185000002384) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.0050508859;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.0071003202;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 114.464996338) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = -0.012511648;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.002118057;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 20.0900001526) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.068644972;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0070954803;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_20 {

        /* Tree 20 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 139.625) node = 2;
                    else node = 3;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 4.29500007629) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.11200233;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0015665704;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -3;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 4;
                    else node = 4;          /* default direction */
                    break;

                case -3:
                    target = -0.004103662;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 158.149993896) node = 5;
                    else node = -6;
                    break;

                case -6:
                    target = 0.0072114157;
                    node = 6;
                    done = true;
                    break;

                case 5:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.024766965;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.12883382;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_21 {

        /* Tree 21 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 4.5) node = 2;
                    else node = 4;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 79.25) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = 0.010681009;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 39.6149978638) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.16993229;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.047247456;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 5;
                    else node = -7;
                    break;

                case -7:
                    target = -0.062888838;
                    node = 7;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 44.0650024414) node = -4;
                    else node = 6;
                    break;

                case -4:
                    target = -0.0039099077;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -5;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = -6;
                    else node = -6;          /* default direction */
                    break;

                case -5:
                    target = -0.0062410222;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0060345742;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_22 {

        /* Tree 22 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 139.5) node = 2;
                    else node = -8;
                    break;

                case -8:
                    target = -0.026878579;
                    node = 8;
                    done = true;
                    break;

                case 2:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 3;
                    else node = -7;
                    break;

                case -7:
                    target = -0.0033573866;
                    node = 7;
                    done = true;
                    break;

                case 3:
                    if (YP_CNT < 3.5) node = 4;
                    else node = -6;
                    break;

                case -6:
                    target = 0.026787694;
                    node = 6;
                    done = true;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 54.5) node = 5;
                    else node = 7;
                    break;

                case 5:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = 6;
                    else node = -3;
                    break;

                case -3:
                    target = 0.020979929;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (PHONE2HOUSEPLACE < 0.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.023617698;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.0023436628;
                    node = 2;
                    done = true;
                    break;

                case 7:
                    if (EATCH_CALL_RATE < 0.444999992847) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.0050381271;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.057118563;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_23 {

        /* Tree 23 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 2;
                    else node = -8;
                    break;

                case -8:
                    target = -0.061742932;
                    node = 8;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD < 78.5749969482) node = 3;
                    else node = 6;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 15.6649999619) node = -1;
                    else node = 4;
                    break;

                case -1:
                    target = -0.01986688;
                    node = 1;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -4;
                    break;

                case -4:
                    target = 0.050369754;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 21.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.015304231;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = -0.002834978;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 125.160003662) node = -5;
                    else node = 7;
                    break;

                case -5:
                    target = -0.0014898076;
                    node = 5;
                    done = true;
                    break;

                case 7:
                    if (CALL_IN_CNT_RSD < 127.395004272) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = 0.076974808;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = 0.011163468;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_24 {

        /* Tree 24 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 3.5) node = 2;
                    else node = 3;
                    break;

                case 2:
                    if (CONTACT_NUM < 89.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.10727845;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.017772203;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.145000010729) node = -3;
                    else node = 4;
                    break;

                case -3:
                    target = -0.012161365;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = 5;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = -6;
                    else node = -6;          /* default direction */
                    break;

                case -6:
                    target = 0.0040285827;
                    node = 6;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.444999992847) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.010947699;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.041411387;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_25 {

        /* Tree 25 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = -0.060142037;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD < 49.1350021362) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.164999991655) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.017896438;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0034873479;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 108.5) node = 5;
                    else node = -6;
                    break;

                case -6:
                    target = -0.011912277;
                    node = 6;
                    done = true;
                    break;

                case 5:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = -3;
                    else node = 6;
                    break;

                case -3:
                    target = 0.0036748536;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 51.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.045533343;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = -0.0015882804;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_26 {

        /* Tree 26 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (EATCH_CALL_RATE < 0.27500000596) node = 2;
                    else node = 4;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -3;
                    break;

                case -3:
                    target = 0.027360996;
                    node = 3;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 97.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0027650538;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0075979495;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -8;
                    break;

                case -8:
                    target = -0.020002274;
                    node = 8;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 25.5) node = 6;
                    else node = 7;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 10.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.020241532;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.041301259;
                    node = 5;
                    done = true;
                    break;

                case 7:
                    if (EATCH_CALL_RATE < 0.435000002384) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = 0.0042743537;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = 0.02590157;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_27 {

        /* Tree 27 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_TIME_STD < 12.8199996948) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.02419827;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 3;
                    else node = -6;
                    break;

                case -6:
                    target = -0.0049882257;
                    node = 6;
                    done = true;
                    break;

                case 3:
                    if (YP_CNT < 1.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (EATCH_CALL_RATE < 0.175000011921) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = -0.008795257;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.0042735013;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (PHONE_USER_TIME < 72.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.017626067;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = -0.0035901227;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_28 {

        /* Tree 28 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 2;
                    else node = 6;
                    break;

                case 2:
                    if (YP_CNT < 1.5) node = 3;
                    else node = 5;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 3.875) node = -1;
                    else node = 4;
                    break;

                case -1:
                    target = 0.09133775;
                    node = 1;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 9.34499931335) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = -0.051276427;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.00026788196;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_CNT_RSD < 52.25) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.00040359166;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.015658333;
                    node = 5;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 7;
                    else node = -8;
                    break;

                case -8:
                    target = 0.083393037;
                    node = 8;
                    done = true;
                    break;

                case 7:
                    if (CONTACT_NUM < 13.5) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = 0.040051241;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = -0.0031949314;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_29 {

        /* Tree 29 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 139.5) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = -0.027196887;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 60.9749984741) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = -0.0040780892;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 4;
                    else node = -6;
                    break;

                case -6:
                    target = 0.023048488;
                    node = 6;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM < 1046) node = 5;
                    else node = -5;
                    break;

                case -5:
                    target = -0.042761951;
                    node = 5;
                    done = true;
                    break;

                case 5:
                    if (PHONE_USER_TIME < 6.5) node = 6;
                    else node = -4;
                    break;

                case -4:
                    target = 0.0042592393;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 3.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.051267281;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = -0.011033728;
                    node = 3;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_30 {

        /* Tree 30 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 4 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 6.5) node = 2;
                    else node = 4;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -3;
                    break;

                case -3:
                    target = -0.0065192562;
                    node = 3;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 734.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.010408653;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.056142973;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 31.5) node = 5;
                    else node = 6;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.105000004172) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.027762252;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.0076084229;
                    node = 5;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 62.5950012207) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = -0.0093104184;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = 0.00035887215;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_31 {

        /* Tree 31 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 3.43499994278) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = 0.11628007;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (EATCH_CALL_RATE < 0.27500000596) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 466.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = -0.0031501674;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.050990963;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -7;
                    break;

                case -7:
                    target = -0.036753399;
                    node = 7;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 288.5) node = 6;
                    else node = -6;
                    break;

                case -6:
                    target = -0.010507634;
                    node = 6;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 59.9550018311) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.0056421134;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.0096303516;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_32 {

        /* Tree 32 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = -0.062356193;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD < 50.625) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = -0.0035897113;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -2;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 4;
                    else node = 4;          /* default direction */
                    break;

                case -2:
                    target = -0.0054764314;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (EATCH_CALL_RATE < 0.414999991655) node = -3;
                    else node = 5;
                    break;

                case -3:
                    target = 0.0063812525;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_CNT_RSD < 96.3349990845) node = 6;
                    else node = -6;
                    break;

                case -6:
                    target = -0.04003856;
                    node = 6;
                    done = true;
                    break;

                case 6:
                    if (PHONE2HOUSEPLACE < 1.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.1358542;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = -0.025017071;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_33 {

        /* Tree 33 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = 0.024875285;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = 6;
                    break;

                case 3:
                    if (CONTACT_NUM < 26.5) node = 4;
                    else node = -4;
                    break;

                case -4:
                    target = 0.00037143811;
                    node = 4;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 147.445007324) node = 5;
                    else node = -3;
                    break;

                case -3:
                    target = 0.046314072;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 76.1699981689) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.016394033;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.016327361;
                    node = 2;
                    done = true;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 70.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.042131477;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -0.056879903;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_34 {

        /* Tree 34 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = 2;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 5;
                    else node = 5;          /* default direction */
                    break;

                case 2:
                    if (EATCH_CALL_RATE < 0.425000011921) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = -0.011867537;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 211.455001831) node = 4;
                    else node = -4;
                    break;

                case -4:
                    target = 0.098840793;
                    node = 4;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 192.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.034149402;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = -0.037034051;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 6;
                    else node = -7;
                    break;

                case -7:
                    target = 0.0063164269;
                    node = 7;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM < 21.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.014201803;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.00077442881;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_35 {

        /* Tree 35 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 7 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 2;
                    else node = -8;
                    break;

                case -8:
                    target = -0.0071982142;
                    node = 8;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 3;
                    else node = -7;
                    break;

                case -7:
                    target = 0.017683234;
                    node = 7;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 301.5) node = 4;
                    else node = -6;
                    break;

                case -6:
                    target = -0.0089327428;
                    node = 6;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 5;
                    else node = -5;
                    break;

                case -5:
                    target = -0.06079584;
                    node = 5;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 109.865005493) node = 6;
                    else node = 7;
                    break;

                case 6:
                    if (EATCH_CALL_RATE < 0.414999991655) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0020565045;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.029046145;
                    node = 2;
                    done = true;
                    break;

                case 7:
                    if (CALL_IN_CNT_RSD < 130.25) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = -0.0017278739;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.021233817;
                    node = 4;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_36 {

        /* Tree 36 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 73.7299957275) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.0038822161;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (PHONE_USER_TIME < 140.5) node = 3;
                    else node = -7;
                    break;

                case -7:
                    target = -0.030664471;
                    node = 7;
                    done = true;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 129.5) node = 4;
                    else node = 6;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -4;
                    break;

                case -4:
                    target = -0.011882464;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 281.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.002955944;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = -0.0082952516;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_TIME_STD < 184.274993896) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.023362607;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.13721582;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_37 {

        /* Tree 37 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 65.2149963379) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.0045255101;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = -2;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 3;
                    else node = 3;          /* default direction */
                    break;

                case -2:
                    target = -0.0069788244;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 4;
                    else node = 6;
                    break;

                case 4:
                    if (YP_CNT < 3.5) node = -3;
                    else node = 5;
                    break;

                case -3:
                    target = 0.0050925621;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_CNT_RSD < 69.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.12378999;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.024290895;
                    node = 5;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 65.9700012207) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = 0.12349869;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = 0.0010945583;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_38 {

        /* Tree 38 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 24.5) node = 2;
                    else node = 3;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 417) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0052956955;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.039866461;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.204999998212) node = 4;
                    else node = -6;
                    break;

                case -6:
                    target = 0.0018205522;
                    node = 6;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 120.694999695) node = -3;
                    else node = 5;
                    break;

                case -3:
                    target = -0.011633917;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 33.7200012207) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.09748346;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.00318227;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_39 {

        /* Tree 39 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 9, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 2;
                    else node = -9;
                    break;

                case -9:
                    target = -0.005460477;
                    node = 9;
                    done = true;
                    break;

                case 2:
                    if (YP_CNT < 3.5) node = 3;
                    else node = 6;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 6.5) node = -1;
                    else node = 4;
                    break;

                case -1:
                    target = -0.011166133;
                    node = 1;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -4;
                    break;

                case -4:
                    target = -0.015224562;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 28.5) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.015280101;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.0028260506;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 7;
                    else node = -8;
                    break;

                case -8:
                    target = -0.018631802;
                    node = 8;
                    done = true;
                    break;

                case 7:
                    if (CONTACT_NUM < 18.5) node = -5;
                    else node = 8;
                    break;

                case -5:
                    target = -0.028741648;
                    node = 5;
                    done = true;
                    break;

                case 8:
                    if (EATCH_CALL_RATE < 0.324999988079) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = 0.036840746;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = -0.0181116;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_40 {

        /* Tree 40 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = -0.059970807;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 3.25) node = -1;
                    else node = 3;
                    break;

                case -1:
                    target = 0.072043866;
                    node = 1;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 228.665008545) node = -2;
                    else node = 4;
                    break;

                case -2:
                    target = -0.00049842062;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = 5;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = 6;
                    else node = 6;          /* default direction */
                    break;

                case 5:
                    if (PHONE_USER_TIME < 119.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = -0.0086227405;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.082184591;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (EATCH_CALL_RATE < 0.384999990463) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.018355522;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -0.014527253;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_41 {

        /* Tree 41 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = 0.027852643;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM < 28.5) node = 3;
                    else node = -6;
                    break;

                case -6:
                    target = -0.0018631293;
                    node = 6;
                    done = true;
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.22499999404) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 166.020004272) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.0036631759;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.075661042;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (PHONE_USER_TIME < 10.5) node = 6;
                    else node = -5;
                    break;

                case -5:
                    target = 0.029492782;
                    node = 5;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 60.6650009155) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.029308822;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = -0.039430293;
                    node = 4;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_42 {

        /* Tree 42 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 43.5) node = 2;
                    else node = -6;
                    break;

                case -6:
                    target = -0.0026770827;
                    node = 6;
                    done = true;
                    break;

                case 2:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = 3;
                    else node = 5;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 200.434997559) node = -1;
                    else node = 4;
                    break;

                case -1:
                    target = 0.0040095752;
                    node = 1;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 234.529998779) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = -0.05078695;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.00031080402;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (YP_CNT != DBL_MISSING_VALUE) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.022865323;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.27243027;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_43 {

        /* Tree 43 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -7;
                    break;

                case -7:
                    target = 0.032074308;
                    node = 7;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM < 28.5) node = 3;
                    else node = 5;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 92.5) node = 4;
                    else node = -3;
                    break;

                case -3:
                    target = -0.025362968;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (EATCH_CALL_RATE < 0.344999998808) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.011174302;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.046179238;
                    node = 2;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.394999980927) node = -4;
                    else node = 6;
                    break;

                case -4:
                    target = -0.0012218933;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM < 103.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.031984515;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0027980834;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_44 {

        /* Tree 44 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = 2;
                    else node = 3;
                    break;

                case 2:
                    if (PHONE2HOUSEPLACE < 1.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0051186773;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0013686621;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 119.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (PHONE_USER_TIME < 10.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.042982225;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = -0.015006313;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_CNT_RSD < 143.154998779) node = -5;
                    else node = 6;
                    break;

                case -5:
                    target = 0.095346586;
                    node = 5;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_TIME_STD < 63.0200004578) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = -0.07266943;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = 0.030096482;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_45 {

        /* Tree 45 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 139.035003662) node = 2;
                    else node = 6;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 122.619995117) node = 3;
                    else node = -5;
                    break;

                case -5:
                    target = -0.014949329;
                    node = 5;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 113.464996338) node = -1;
                    else node = 4;
                    break;

                case -1:
                    target = -0.00083700875;
                    node = 1;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -4;
                    break;

                case -4:
                    target = -0.13238232;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 493) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = -0.015868563;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.062748413;
                    node = 3;
                    done = true;
                    break;

                case 6:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 7;
                    else node = -8;
                    break;

                case -8:
                    target = 0.022922715;
                    node = 8;
                    done = true;
                    break;

                case 7:
                    if (CONTACT_NUM < 315.5) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = 0.0071385172;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = -0.010071281;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_46 {

        /* Tree 46 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (EATCH_CALL_RATE < 0.254999995232) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.0027049633;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 15.7199993134) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (CALL_IN_CNT_RSD < 11.0900001526) node = -2;
                    else node = -3;
                    break;

                case -2:
                    target = 0.015869407;
                    node = 2;
                    done = true;
                    break;

                case -3:
                    target = 0.14215106;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 61.5299987793) node = -4;
                    else node = 5;
                    break;

                case -4:
                    target = -0.0012045322;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (PHONE_USER_TIME < 52.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.012877661;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -5.9350225e-006;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_47 {

        /* Tree 47 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 3.5) node = 2;
                    else node = 3;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 169) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.020117781;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.10763601;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (EATCH_CALL_RATE < 0.22499999404) node = 4;
                    else node = -7;
                    break;

                case -7:
                    target = 0.0022642493;
                    node = 7;
                    done = true;
                    break;

                case 4:
                    if (YP_CNT != DBL_MISSING_VALUE) node = 5;
                    else node = 6;
                    break;

                case 5:
                    if (YP_CNT < 4.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = -0.0034301829;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.041179988;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (CALL_IN_CNT_RSD < 30.5) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.040037409;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -0.0097533997;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_48 {

        /* Tree 48 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 1098.90991211) node = 2;
                    else node = -6;
                    break;

                case -6:
                    target = 0.09910305;
                    node = 6;
                    done = true;
                    break;

                case 2:
                    if (PHONE_USER_TIME < 3.5) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (CONTACT_NUM < 95.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.056246738;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.014010857;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (YP_CNT != DBL_MISSING_VALUE) node = -3;
                    else node = 5;
                    break;

                case -3:
                    target = 0.0019288506;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (PHONE2HOUSEPLACE != DBL_MISSING_VALUE) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = -0.0052223569;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = 0.083359057;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_49 {

        /* Tree 49 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 4.19000005722) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = 0.071513632;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 327.25) node = -2;
                    else node = 3;
                    break;

                case -2:
                    target = -0.0015148376;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (CONTACT_NUM < 97.5) node = -3;
                    else node = 4;
                    break;

                case -3:
                    target = 0.042771896;
                    node = 3;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 331.25) node = -4;
                    else node = 5;
                    break;

                case -4:
                    target = 0.15749698;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 384.765014648) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = -0.005517619;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.069885163;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_50 {

        /* Tree 50 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 7, Depth = 5 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 31.5) node = 2;
                    else node = 6;
                    break;

                case 2:
                    if (PHONE_USER_TIME < 6.5) node = 3;
                    else node = 4;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 2.5) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.058855777;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0079781315;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 5;
                    else node = -5;
                    break;

                case -5:
                    target = 0.046585289;
                    node = 5;
                    done = true;
                    break;

                case 5:
                    if (CONTACT_NUM < 22.5) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.022874168;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = 0.0058578941;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (EATCH_CALL_RATE < 0.215000003576) node = -6;
                    else node = -7;
                    break;

                case -6:
                    target = -0.0091280242;
                    node = 6;
                    done = true;
                    break;

                case -7:
                    target = 0.00042481852;
                    node = 7;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_51 {

        /* Tree 51 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 4 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 17.4500007629) node = 2;
                    else node = 4;
                    break;

                case 2:
                    if (CALL_IN_TIME_STD < 68.1050033569) node = 3;
                    else node = -3;
                    break;

                case -3:
                    target = -0.047651223;
                    node = 3;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 19.9500007629) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.0094661012;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.059296691;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = -4;
                    else node = 5;
                    break;

                case -4:
                    target = -0.00046724068;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.245000004768) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = 0.073527506;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = -0.0083925495;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_52 {

        /* Tree 52 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CALL_IN_CNT_RSD < 22.2900009155) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.021783695;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (DM_TEL_NOUSE_BASEDAYS == 0
                            ) node = 3;
                    else if (DM_TEL_NOUSE_BASEDAYS == 1
                            ) node = -6;
                    else node = -6;          /* default direction */
                    break;

                case -6:
                    target = 0.0010787413;
                    node = 6;
                    done = true;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 47.5) node = -2;
                    else node = 4;
                    break;

                case -2:
                    target = 0.002015892;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 91.0250015259) node = -3;
                    else node = 5;
                    break;

                case -3:
                    target = -0.026690742;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 92.2249984741) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.12329723;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = -0.0054577992;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_53 {

        /* Tree 53 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 6 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 5.5) node = -1;
                    else node = 2;
                    break;

                case -1:
                    target = -0.009039822;
                    node = 1;
                    done = true;
                    break;

                case 2:
                    if (PHONE_USER_TIME < 19.5) node = 3;
                    else node = -6;
                    break;

                case -6:
                    target = 0.00049299494;
                    node = 6;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD < 173.584991455) node = -2;
                    else node = 4;
                    break;

                case -2:
                    target = 0.005658395;
                    node = 2;
                    done = true;
                    break;

                case 4:
                    if (CONTACT_NUM < 72) node = -3;
                    else node = 5;
                    break;

                case -3:
                    target = -0.00049454993;
                    node = 3;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_CNT_RSD < 336.5) node = -4;
                    else node = -5;
                    break;

                case -4:
                    target = 0.077424029;
                    node = 4;
                    done = true;
                    break;

                case -5:
                    target = -0.011182703;
                    node = 5;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_54 {

        /* Tree 54 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 8, Depth = 8 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (CONTACT_NUM != DBL_MISSING_VALUE) node = 2;
                    else node = -8;
                    break;

                case -8:
                    target = 0.0083458878;
                    node = 8;
                    done = true;
                    break;

                case 2:
                    if (CONTACT_NUM < 825) node = 3;
                    else node = -7;
                    break;

                case -7:
                    target = -0.029446802;
                    node = 7;
                    done = true;
                    break;

                case 3:
                    if (CALL_IN_TIME_STD != DBL_MISSING_VALUE) node = 4;
                    else node = -6;
                    break;

                case -6:
                    target = -0.05921071;
                    node = 6;
                    done = true;
                    break;

                case 4:
                    if (CALL_IN_TIME_STD < 458.474975586) node = 5;
                    else node = -5;
                    break;

                case -5:
                    target = -0.042756485;
                    node = 5;
                    done = true;
                    break;

                case 5:
                    if (CALL_IN_TIME_STD < 23.8549995422) node = 6;
                    else node = -4;
                    break;

                case -4:
                    target = 0.0022188647;
                    node = 4;
                    done = true;
                    break;

                case 6:
                    if (PHONE_USER_TIME < 78.5) node = 7;
                    else node = -3;
                    break;

                case -3:
                    target = -0.030703552;
                    node = 3;
                    done = true;
                    break;

                case 7:
                    if (EATCH_CALL_RATE < 0.435000002384) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = -0.0027517902;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = 0.064964706;
                    node = 2;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    private class tree_55 {

        /* Tree 55 of 55 */
        public double score() {
            double target;
            int node;
            boolean done;

      /* N terminal nodes = 6, Depth = 4 */

            target = 0.0;
            node = 1; /* start at root node */
            done = false; /* set at terminal node */

            while (!done) switch (node) {

                case 1:
                    if (PHONE_USER_TIME < 6.5) node = 2;
                    else node = 3;
                    break;

                case 2:
                    if (CALL_IN_CNT_RSD < 14.7749996185) node = -1;
                    else node = -2;
                    break;

                case -1:
                    target = 0.072435329;
                    node = 1;
                    done = true;
                    break;

                case -2:
                    target = -0.0074429394;
                    node = 2;
                    done = true;
                    break;

                case 3:
                    if (PHONE_USER_TIME < 19.5) node = 4;
                    else node = 5;
                    break;

                case 4:
                    if (CALL_IN_CNT_RSD < 402) node = -3;
                    else node = -4;
                    break;

                case -3:
                    target = 0.0065855007;
                    node = 3;
                    done = true;
                    break;

                case -4:
                    target = -0.07207036;
                    node = 4;
                    done = true;
                    break;

                case 5:
                    if (EATCH_CALL_RATE < 0.175000011921) node = -5;
                    else node = -6;
                    break;

                case -5:
                    target = -0.0057246819;
                    node = 5;
                    done = true;
                    break;

                case -6:
                    target = 0.0013815987;
                    node = 6;
                    done = true;
                    break;

                default: /* error */
                    target = 0.0;
                    done = true;
                    node = 0;
                    break;
            }
            return target;
        }
    }

    /*************************************************
     * Stub to call the model
     *************************************************/

    public double getProbability(Float eatch_Call_Rate,Integer yp_cnt,Integer phone_user_time,Integer contact_num,Integer call_in_cnt_rsd,Integer dm_tel_nouse_basedays,Double call_in_time_std,Integer phone2houseplace) {


    /* Initialize values for predictors here */
        //"CALL_IN_CNT_RSD", "CALL_IN_TIME_STD",
        //"EATCH_CALL_RATE", "PHONE_USER_TIME",
        //       "YP_CNT", "PHONE2HOUSEPLACE", "CONTACT_NUM"


            TreenetScore tns = new TreenetScore();
            int i;

            double[] doubleParms = new double[7];
            doubleParms[0] = (call_in_cnt_rsd == null) ? 0 : call_in_cnt_rsd;
            doubleParms[1] = (call_in_time_std == null) ? 0 : call_in_time_std;
            doubleParms[2] = (eatch_Call_Rate == null) ? 0 : eatch_Call_Rate;
            doubleParms[3] = (phone_user_time == null) ? 0 : phone_user_time;
            doubleParms[4] = (yp_cnt == null) ? 0 : yp_cnt;
            doubleParms[5] = (phone2houseplace == null) ? 0 : phone2houseplace;
            doubleParms[6] = (contact_num == null) ? 0 : contact_num;

            int[] intParms = new int[1];
            intParms[0] = (dm_tel_nouse_basedays == null) ? 0 : dm_tel_nouse_basedays;


            tns.setDoubleVariables(doubleParms);
            //DM_TEL_NOUSE_BASEDAYS
            tns.setIntVariables(intParms);

            int[] value = tns.targetClass();
            double[] prob = tns.getScore();
            /*for (i = 0; i < prob.length; i++) {
                //System.out.format("prob(%d)=%f\n", value[i], prob[i]);

            }*/
            return prob[1];
    }
}
