package io.firogence.mobile_banking_channel_request_processor.enums;

/**
 * @author Alex Kiburu
 */
public enum ExpenseType {
    CUSTOMER_EXPENSE("Customer Expense"),
    ENTITY_EXPENSE("Entity Expense");

    private final String displayValue;

    ExpenseType(String displayValue) {
        this.displayValue = displayValue;
    }

    // This getter can be used for display or API clarity
    public String getDisplayValue() {
        return displayValue;
    }
}
