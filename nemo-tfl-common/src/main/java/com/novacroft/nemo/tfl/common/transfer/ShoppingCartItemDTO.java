package com.novacroft.nemo.tfl.common.transfer;

import java.io.Serializable;

public class ShoppingCartItemDTO implements Serializable {

    private static final long serialVersionUID = -7196928827841074153L;

    private String item;
    private String startDate;
    private String endDate;
    private Integer price;
    private Integer creditBalance;
    private String ticketType;
    private Integer topUpAmount;
    private Integer autoTopUpAmount;
    private String formattedPrice;
    private String formattedAutoTopUpAmount;
    private String activeDate;
    private String expiryDate;
    private String reminderDate;
    private Long cardId;
    private Long id;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Integer creditBalance) {
        this.creditBalance = creditBalance;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Integer getAutoTopUpAmount() {
        return autoTopUpAmount;
    }

    public void setAutoTopUpAmount(Integer autoTopUpAmount) {
        this.autoTopUpAmount = autoTopUpAmount;
    }

    public String getFormattedPrice() {
        return formattedPrice;
    }

    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
    }

    public String getFormattedAutoTopUpAmount() {
        return formattedAutoTopUpAmount;
    }

    public void setFormattedAutoTopUpAmount(String formattedAutoTopUpAmount) {
        this.formattedAutoTopUpAmount = formattedAutoTopUpAmount;
    }

    public Integer getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(Integer topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((activeDate == null) ? 0 : activeDate.hashCode());
        result = prime * result + ((autoTopUpAmount == null) ? 0 : autoTopUpAmount.hashCode());
        result = prime * result + ((cardId == null) ? 0 : cardId.hashCode());
        result = prime * result + ((creditBalance == null) ? 0 : creditBalance.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
        result = prime * result + ((formattedAutoTopUpAmount == null) ? 0 : formattedAutoTopUpAmount.hashCode());
        result = prime * result + ((formattedPrice == null) ? 0 : formattedPrice.hashCode());
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((reminderDate == null) ? 0 : reminderDate.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((ticketType == null) ? 0 : ticketType.hashCode());
        result = prime * result + ((topUpAmount == null) ? 0 : topUpAmount.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShoppingCartItemDTO other = (ShoppingCartItemDTO) obj;
        if (activeDate == null) {
            if (other.activeDate != null) {
                return false;
            }
        } else if (!activeDate.equals(other.activeDate)) {
            return false;
        }
        if (autoTopUpAmount == null) {
            if (other.autoTopUpAmount != null) {
                return false;
            }
        } else if (!autoTopUpAmount.equals(other.autoTopUpAmount)) {
            return false;
        }
        if (cardId == null) {
            if (other.cardId != null) {
                return false;
            }
        } else if (!cardId.equals(other.cardId)) {
            return false;
        }
        if (creditBalance == null) {
            if (other.creditBalance != null) {
                return false;
            }
        } else if (!creditBalance.equals(other.creditBalance)) {
            return false;
        }
        if (endDate == null) {
            if (other.endDate != null) {
                return false;
            }
        } else if (!endDate.equals(other.endDate)) {
            return false;
        }
        if (expiryDate == null) {
            if (other.expiryDate != null) {
                return false;
            }
        } else if (!expiryDate.equals(other.expiryDate)) {
            return false;
        }
        if (formattedAutoTopUpAmount == null) {
            if (other.formattedAutoTopUpAmount != null) {
                return false;
            }
        } else if (!formattedAutoTopUpAmount.equals(other.formattedAutoTopUpAmount)) {
            return false;
        }
        if (formattedPrice == null) {
            if (other.formattedPrice != null) {
                return false;
            }
        } else if (!formattedPrice.equals(other.formattedPrice)) {
            return false;
        }
        if (item == null) {
            if (other.item != null) {
                return false;
            }
        } else if (!item.equals(other.item)) {
            return false;
        }
        if (price == null) {
            if (other.price != null) {
                return false;
            }
        } else if (!price.equals(other.price)) {
            return false;
        }
        if (reminderDate == null) {
            if (other.reminderDate != null) {
                return false;
            }
        } else if (!reminderDate.equals(other.reminderDate)) {
            return false;
        }
        if (startDate == null) {
            if (other.startDate != null) {
                return false;
            }
        } else if (!startDate.equals(other.startDate)) {
            return false;
        }
        if (ticketType == null) {
            if (other.ticketType != null) {
                return false;
            }
        } else if (!ticketType.equals(other.ticketType)) {
            return false;
        }
        if (topUpAmount == null) {
            if (other.topUpAmount != null) {
                return false;
            }
        } else if (!topUpAmount.equals(other.topUpAmount)) {
            return false;
        }
        return true;
    }

}
