package com.ishareread.common.constant;

public class PhaseConstants {
	private PhaseConstants() {
	}
	
	public static final String SELF = "self";//自评
	public static final String CHECK = "check";//检查  领导审核 
	public static final String ASSIGNED = "assigned";//指派    市场部指派 
	public static final String REVIEW = "review";//复评    专家复评
	public static final String AUDIT = "audit";//审核   市场部检查
	public static final String RE_AUDIT = "re_audit";//复审   网信部
	public static final String END = "end";//结束
	
	/**
	 * 返回下一个流程节点
	 * @param now
	 * @return
	 */
	public static String next(String now) {
		if(now.equals(SELF)) {
			return CHECK;
		}else if(now.equals(CHECK)) {
			return ASSIGNED;
		}else if(now.equals(ASSIGNED)) {
			return REVIEW;
		}else if(now.equals(REVIEW)) {
			return AUDIT;
		}else if(now.equals(AUDIT)) {
			return RE_AUDIT;
		}else if(now.equals(RE_AUDIT)) {
			return END;
		}
		return SELF;
	}
}
