package com.africa.crm.businessmanagement.network.api;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/4 0004 14:32
 * Modification  History:
 * Why & What is modified:
 */
public class RequestMethod {
    //企业信息列表
    public static final String REQUEST_COMPANY_INFO_LIST = "request_company_info_list";
    //删除企业信息
    public static final String REQUEST_DELETE_COMPANY_INFO = "request_delete_company_info";
    //企业类型
    public static final String REQUEST_COMPANY_TYPE = "request_company_type";
    //企业状态
    public static final String REQUEST_COMPANY_STATE = "request_company_state";
    //上传图片
    public static final String REQUEST_UPLOAD_IMAGE = "request_upload_image";
    //获取企业信息详情
    public static final String REQUEST_COMPANY_INFO_DETAIL = "request_company_info_detail";
    //保存企业信息
    public static final String REQUEST_SAVE_COMPANY_INFO = "request_save_company_info";

    //所有用户角色
    public static final String REQUEST_QUERY_ALL_ROLES = "request_query_all_roles";
    //企业账号列表
    public static final String REQUEST_COMPANY_ACCOUNT_LIST = "request_company_account_list";
    //删除企业账号
    public static final String REQUEST_DELETE_COMPANY_ACCOUNT = "request_delete_company_account";
    //获取企业账号详情
    public static final String REQUEST_COMPANY_ACCOUNT_DETAIL = "request_company_account_detail";
    //保存企业账号
    public static final String REQUEST_SAVE_COMPANY_ACCOUNT = "request_save_company_account";

    //供应商分类
    public static final String REQUEST_SUPPLIER_TYPE = "request_supplier_type";
    //企业供应商列表
    public static final String REQUEST_COMPANY_SUPPLIER_LIST = "request_company_supplier_list";
    //获取企业供应商详情
    public static final String REQUEST_COMPANY_SUPPLIER_DETAIL = "request_company_supplier_detail";
    //删除企业供应商
    public static final String REQUEST_DELETE_COMPANY_SUPPLIER = "request_delete_company_supplier";
    //保存企业供应商
    public static final String REQUEST_SAVE_COMPANY_SUPPLIER = "request_save_company_supplier";

    //产品分类
    public static final String REQUEST_PRODUCT_TYPE = "request_product_type";
    //企业产品列表
    public static final String REQUEST_COMPANY_PRODUCT_LIST = "request_company_product_list";
    //删除企业产品
    public static final String REQUEST_DELETE_COMPANY_PRODUCT = "request_delete_company_product";
    //获取所有供应商列表
    public static final String REQUEST_ALL_SUPPLIER_LIST = "request_all_supplier_list";
    //获取企业产品详情
    public static final String REQUEST_COMPANY_PRODUCT_DETAIL = "request_company_product_detail";
    //保存企业产品
    public static final String REQUEST_SAVE_COMPANY_PRODUCT = "request_save_company_product";

    //行业分类
    public static final String REQUEST_INDUSTRY_TYPE = "request_industry_type";
    //企业客户列表
    public static final String REQUEST_COMPANY_CLIENT_LIST = "request_company_client_list";
    //删除企业客户
    public static final String REQUEST_DELETE_COMPANY_CLIENT = "request_delete_company_client";
    //获取所有用户列表
    public static final String REQUEST_ALL_USERS_LIST = "request_all_users_list";
    //获取企业客户详情
    public static final String REQUEST_COMPANY_CLIENT_DETAIL = "request_company_client_detail";
    //保存企业客户
    public static final String REQUEST_SAVE_COMPANY_CLIENT = "request_save_company_client";

    //联系人来源分类
    public static final String REQUEST_CONTACT_FROM_TYPE = "request_contact_from_type";
    //企业联系人列表
    public static final String REQUEST_COMPANY_CONTACT_LIST = "request_company_contact_list";
    //删除企业联系人
    public static final String REQUEST_DELETE_COMPANY_CONTACT = "request_delete_company_contact";
    //获取企业联系人详情
    public static final String REQUEST_COMPANY_CONTACT_DETAIL = "request_company_contact_detail";
    //保存企业联系人
    public static final String REQUEST_SAVE_COMPANY_CONTACT = "request_save_company_contact";

    //企业交易单列表
    public static final String REQUEST_COMPANY_TRADING_ORDER_LIST = "request_company_trading_order_list";
    //删除企业交易单
    public static final String REQUEST_DELETE_COMPANY_TRADING_ORDER = "request_delete_company_trading_order";
    //获取所有联系人列表
    public static final String REQUEST_ALL_CONTACT_LIST = "request_all_contact_list";
    //获取所有客户列表
    public static final String REQUEST_ALL_CUSTOMER_LIST = "request_all_customer_list";
    //获取企业交易单详情
    public static final String REQUEST_COMPANY_TRADING_ORDER_DETAIL = "request_company_trading_order_detail";
    //保存企业交易单
    public static final String REQUEST_SAVE_COMPANY_TRADING_ORDER = "request_save_company_trading_order";

    //企业报价单列表
    public static final String REQUEST_COMPANY_QUOTATION_LIST = "request_company_quotation_list";
    //删除企业报价单
    public static final String REQUEST_DELETE_COMPANY_QUOTATION = "request_delete_company_quotation";
    //获取所有产品
    public static final String REQUEST_ALL_PRODUCTS_LIST = "request_all_products_list";
    //获取企业报价单详情
    public static final String REQUEST_COMPANY_QUOTATION_DETAIL = "request_company_trading_quotation_detail";
    //保存企业报价单
    public static final String REQUEST_SAVE_COMPANY_QUOTATION = "request_save_company_quotation";

    //销售单状态
    public static final String REQUEST_SALE_ORDER_STATE = "request_sale_order_state";
    //企业销售单列表
    public static final String REQUEST_SALE_ORDER_LIST = "request_sale_order_list";
    //删除企业销售单
    public static final String REQUEST_DELETE_SALE_ORDER = "request_delete_sale_order";
    //获取企业销售单详情
    public static final String REQUEST_COMPANY_SALE_ORDER_DETAIL = "request_company_sale_order_detail";
    //保存企业销售单
    public static final String REQUEST_SAVE_COMPANY_SALE_ORDER = "request_save_company_sale_order";

    //企业发货单列表
    public static final String REQUEST_DELIVERY_ORDER_LIST = "request_delivery_order_list";
    //删除企业发货单
    public static final String REQUEST_DELETE_DELIVERY_ORDER = "request_delete_delivery_order";
    //发货单状态
    public static final String REQUEST_INVOICE_STATE = "request_invoice_state";
    //获取全部销售单
    public static final String REQUEST_ALL_SALES_ORDER = "request_all_sales_order";
    //获取企业发货单详情
    public static final String REQUEST_COMPANY_DELIVERY_ORDER_DETAIL = "request_company_delivery_order_detail";
    //保存企业发货单
    public static final String REQUEST_SAVE_COMPANY_DELIVERY_ORDER = "request_save_company_delivery_order";

    //企业付款单列表
    public static final String REQUEST_PAY_ORDER_LIST = "request_pay_order_list";
    //删除企业付款单
    public static final String REQUEST_DELETE_PAY_ORDER = "request_delete_pay_order";
    //获取全部交易单
    public static final String REQUEST_ALL_TRADING_ORDER = "request_all_trading_order";
    //获取企业付款单详情
    public static final String REQUEST_COMPANY_PAY_ORDER_DETAIL = "request_company_delivery_pay_detail";
    //保存企业付款单
    public static final String REQUEST_SAVE_COMPANY_PAY_ORDER = "request_save_company_pay_order";

    //服务状态
    public static final String REQUEST_SERVICE_STATE = "request_service_state";
    //服务类型
    public static final String REQUEST_SERVICE_TYPE = "request_service_type";
    //企业服务记录列表
    public static final String REQUEST_SERVICE_RECORD_LIST = "request_service_record_list";
    //删除企业服务记录
    public static final String REQUEST_DELETE_SERVICE_RECORD = "request_delete_service_record";
    //服务优先等級
    public static final String REQUEST_SERVICE_LEVEL = "request_service_level";
    //获取企业服务记录详情
    public static final String REQUEST_COMPANY_SERVICE_RECORD_DETAIL = "request_company_service_record_detail";
    //保存企业服务记录
    public static final String REQUEST_SAVE_COMPANY_SERVICE_RECORD = "request_save_company_service_record";

    //库存类型
    public static final String REQUEST_STOCK_TYPE = "request_stock_type";
    //企业库存管理列表
    public static final String REQUEST_INVENTORY_LIST = "request_inventory_list";
    //获取企业库存详情
    public static final String REQUEST_COMPANY_INVENTORY_DETAIL = "request_company_inventory_detail";
    //保存企业库存
    public static final String REQUEST_SAVE_COMPANY_INVENTORY = "request_save_company_inventory";

    //企业采购管理列表
    public static final String REQUEST_COMPANY_PURCHASING_LIST = "request_company_purchasing_list";
    //删除企业采购管理
    public static final String REQUEST_DELETE_COMPANY_PURCHASING = "request_delete_company_purchasing";
    //采购单状态
    public static final String REQUEST_PURCHASING_STATE = "request_purchasing_state";
    //获取企业采购单详情
    public static final String REQUEST_COMPANY_PURCHASING_DETAIL = "request_company_purchasing_detail";
    //保存企业采购单
    public static final String REQUEST_SAVE_COMPANY_PURCHASING = "request_save_company_purchasing";

    //任务状态
    public static final String REQUEST_TASK_STATE = "request_task_state";
    //任务优先等級
    public static final String REQUEST_TASK_LEVEL = "request_task_level";
    //企业任务管理列表
    public static final String REQUEST_COMPANY_TASK_LIST = "request_company_task_list";
    //删除企业任务管理
    public static final String REQUEST_DELETE_COMPANY_TASK = "request_delete_company_task";
    //获取企业任务详情
    public static final String REQUEST_COMPANY_TASK_DETAIL = "request_company_task_detail";
    //保存企业任务
    public static final String REQUEST_SAVE_COMPANY_TASK = "request_save_company_task";

    //企业PDF文件列表
    public static final String REQUEST_COMPANY_PDF_LIST = "request_company_pdf_list";
    //删除企业PDF文件
    public static final String REQUEST_DELETE_COMPANY_PDF = "request_delete_company_pdf";
    //上传PDF文件
    public static final String REQUEST_UPLOAD_PDF_FILE = "request_upload_pdf_file";
    //下载PDF文件
    public static final String REQUEST_DOWNLOAD_PDF_FILE = "request_download_pdf_file";
    //获取企业PDF文件详情
    public static final String REQUEST_COMPANY_PDF_DETAIL = "request_company_pdf_detail";
    //保存企业PDF文件
    public static final String REQUEST_SAVE_COMPANY_PDF = "request_save_company_pdf";

    //企业包装数据管理列表
    public static final String REQUEST_COMPANY_PACKAGING_DATA_LIST = "request_company_packaging_data_list";
    //获取企业包装数据详情
    public static final String REQUEST_COMPANY_PACKAGING_DATA_DETAIL = "request_company_packaging_data_detail";
    //校验日期
    public static final String REQUEST_CHECK_DATE = "request_check_date";

    //企业预算管理列表
    public static final String REQUEST_COMPANY_EXPENDITURE_A_LIST = "request_company_expenditure_a_list";
    //获取企业预算详情
    public static final String REQUEST_COMPANY_EXPENDITURE_A_DETAIL = "request_company_expenditure_a_detail";
    //获取企业支出记录
    public static final String REQUEST_COMPANY_PAY_RECORD = "request_company_pay_record";
    //校验企业预算日期
    public static final String REQUEST_CHECK_YS_DATE = "request_check_ys_date";

    //企业支出管理列表
    public static final String REQUEST_COMPANY_EXPENDITURE_B_LIST = "request_company_expenditure_b_list";
    //获取企业支出详情
    public static final String REQUEST_COMPANY_EXPENDITURE_B_DETAIL = "request_company_expenditure_b_detail";
    //保存企业支出
    public static final String REQUEST_SAVE_COMPANY_EXPENDITURE_B = "request_save_company_expenditure_b";

    //企业角色管理列表
    public static final String REQUEST_COMPANY_SYSTEM_ROLE_LIST = "request_company_system_role_list";
    //获取企业角色详情
    public static final String REQUEST_COMPANY_SYSTEM_ROLE_DETAIL = "request_company_system_role_detail";
    //企业用户管理列表
    public static final String REQUEST_COMPANY_SYSTEM_USER_LIST = "request_company_system_user_list";
    //删除企业用户
    public static final String REQUEST_DELETE_COMPANY_SYSTEM_USER = "request_delete_company_system_user";
    //用户类型
    public static final String REQUEST_USER_TYPE = "request_user_type";
    //获取用户信息
    public static final String REQUEST_GET_USER_INFO = "request_get_user_info";
    //获取所有公司
    public static final String REQUEST_GET_ALL_COMPANY = "request_get_all_company";
    //保存企业用户信息
    public static final String REQUEST_SAVE_COMPANY_USER = "request_save_company_user";
}
