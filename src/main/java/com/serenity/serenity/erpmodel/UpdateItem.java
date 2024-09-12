package com.serenity.serenity.erpmodel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UpdateItem {
     @SerializedName("name")
        private String name;

        @SerializedName("owner")
        private String owner;

        @SerializedName("creation")
        private String creation;

        @SerializedName("modified")
        private String modified;

        @SerializedName("modified_by")
        private String modifiedBy;

        @SerializedName("docstatus")
        private int docStatus;

        @SerializedName("idx")
        private int idx;

        @SerializedName("barcode")
        private String barcode;

        @SerializedName("has_item_scanned")
        private int hasItemScanned;

        @SerializedName("s_warehouse")
        private String sourceWarehouse;

        @SerializedName("t_warehouse")
        private String targetWarehouse;

        @SerializedName("item_code")
        private String itemCode;

        @SerializedName("item_name")
        private String itemName;

        @SerializedName("is_finished_item")
        private int isFinishedItem;

        @SerializedName("is_scrap_item")
        private int isScrapItem;

        @SerializedName("quality_inspection")
        private String qualityInspection;

        @SerializedName("subcontracted_item")
        private String subcontractedItem;

        @SerializedName("description")
        private String description;

        @SerializedName("item_group")
        private String itemGroup;

        @SerializedName("image")
        private String image;

        @SerializedName("qty")
        private double quantity;

        @SerializedName("transfer_qty")
        private double transferQuantity;

        @SerializedName("retain_sample")
        private int retainSample;

        @SerializedName("uom")
        private String uom;

        @SerializedName("stock_uom")
        private String stockUom;

        @SerializedName("conversion_factor")
        private double conversionFactor;

        @SerializedName("sample_quantity")
        private int sampleQuantity;

        @SerializedName("basic_rate")
        private double basicRate;

        @SerializedName("additional_cost")
        private double additionalCost;

        @SerializedName("valuation_rate")
        private double valuationRate;

        @SerializedName("allow_zero_valuation_rate")
        private int allowZeroValuationRate;

        @SerializedName("set_basic_rate_manually")
        private int setBasicRateManually;

        @SerializedName("basic_amount")
        private double basicAmount;

        @SerializedName("amount")
        private double amount;

        @SerializedName("use_serial_batch_fields")
        private int useSerialBatchFields;

        @SerializedName("serial_and_batch_bundle")
        private String serialAndBatchBundle;

        @SerializedName("serial_no")
        private String serialNo;

        @SerializedName("batch_no")
        private String batchNo;

        @SerializedName("expense_account")
        private String expenseAccount;

        @SerializedName("cost_center")
        private String costCenter;

        @SerializedName("project")
        private String project;

        @SerializedName("actual_qty")
        private double actualQty;

        @SerializedName("transferred_qty")
        private double transferredQty;

        @SerializedName("bom_no")
        private String bomNo;

        @SerializedName("allow_alternative_item")
        private int allowAlternativeItem;

        @SerializedName("material_request")
        private String materialRequest;

        @SerializedName("material_request_item")
        private String materialRequestItem;

        @SerializedName("original_item")
        private String originalItem;

        @SerializedName("against_stock_entry")
        private String againstStockEntry;

        @SerializedName("ste_detail")
        private String steDetail;

        @SerializedName("po_detail")
        private String poDetail;

        @SerializedName("sco_rm_detail")
        private String scoRmDetail;

        @SerializedName("putaway_rule")
        private String putawayRule;

        @SerializedName("reference_purchase_receipt")
        private String referencePurchaseReceipt;

        @SerializedName("job_card_item")
        private String jobCardItem;

        @SerializedName("parent")
        private String parent;

        @SerializedName("parentfield")
        private String parentField;

        @SerializedName("parenttype")
        private String parentType;

        @SerializedName("doctype")
        private String doctype;

        @SerializedName("__unsaved")
        private int unsaved;
}
