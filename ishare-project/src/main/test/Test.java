import java.io.FileNotFoundException;
import java.io.IOException;

import com.ishareread.common.utils.DateUtils;

public class Test {
	public static void main(String[] args) throws FileNotFoundException, IOException {
//		String data  = "{\"version\":\"2018\",\"threatTypes\":\"内容安全\",\"code\":\"2.1.1-FX-1-1\",\"threateningAssignment\":\"3\",\"assessmentPoints\":\"评估人员通过人员访谈的方式，检查该业务应用是否存在多级账号，导致越权的风险及特权账号滥用等问题。\",\"bz_obj\":[{\"scoringManner\":\"否决项\",\"code\":\"2.1.1-BZ-1-1\",\"vulnerabilityAssignment\":\"2\",\"securityPoint\":\"评估人员通过文件审查的方式，检查该业务在创建账户时，是否建立权限审批流程。\",\"criteria\":\"若评估过程中经检查发现未在创建账户时建立权限审批流程，此项视为不符要求。\",\"cj_obj\":[{\"scenarioTypes\":\"制度类\",\"autoDetection\":\"Y\",\"code\":\"2.1.1-BZ-1-1-1\",\"scenarioName\":\"文件审查\",\"modeExecution\":\"文件审查\",\"scenarioDescription\":\"评估人员通过文件审查的方式，检查该业务在创建账户时，是否建立权限审批流程。\"}]}]}";
//		JSONObject object = JSONObject.parseObject(data);
//		if(object.containsKey("bz_obj")) {
//			System.out.println("评估版本:"+object.getString("version"));
//			System.out.println("威胁类型:"+object.getString("threatTypes"));
//			System.out.println("FX(威胁点)编号:"+object.getString("code"));
//			System.out.println("威胁赋值:"+object.getString("threateningAssignment"));
//			System.out.println("安全风险评估要点:"+object.getString("assessmentPoints"));
//			
//			JSONArray bzArray = object.getJSONArray("bz_obj");
//			if(bzArray.size()>0 && bzArray.getJSONObject(0).containsKey("cj_obj")) {
//				for(int i=0;i<bzArray.size();i++) {
//					System.out.println("\tBZ(保障点)编号:"+bzArray.getJSONObject(i).getString("code"));
//					System.out.println("\t评分方式:"+bzArray.getJSONObject(i).getString("scoringManner"));
//					System.out.println("\t脆弱性赋值:"+bzArray.getJSONObject(i).getString("vulnerabilityAssignment"));
//					System.out.println("\t企业保障能力评估要点:"+bzArray.getJSONObject(i).getString("securityPoint"));
//					System.out.println("\t评判标准:"+bzArray.getJSONObject(i).getString("criteria"));
//					System.out.println("\t测评方法:"+bzArray.getJSONObject(i).getString("assessmentMethod"));
//					
//					JSONArray cjArray = bzArray.getJSONObject(i).getJSONArray("cj_obj");
//					for (int j = 0; j < cjArray.size(); j++) {
//						System.out.println("\t\t场景编号:"+cjArray.getJSONObject(j).getString("code"));
//						System.out.println("\t\t场景名称:"+cjArray.getJSONObject(j).getString("scenarioName"));
//						System.out.println("\t\t场景类型:"+cjArray.getJSONObject(j).getString("scenarioTypes"));
//						System.out.println("\t\t自动检测:"+cjArray.getJSONObject(j).getString("autoDetection"));
//						System.out.println("\t\t评估方法:"+cjArray.getJSONObject(j).getString("modeExecution"));
//						System.out.println("\t\t场景描述:"+cjArray.getJSONObject(j).getString("scenarioDescription"));
//					}
//				}
//			}
//		}

		System.out.println("崽出生多久了:"+DateUtils.getDatePoor(DateUtils.parseDate("2019-02-05 16:09:00"), DateUtils.getNowDate()));
		System.out.println("百天："+DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.addDays(DateUtils.parseDate("2019-02-05"), 100)));

	}

}
