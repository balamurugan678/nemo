package com.novacroft.nemo.tfl.innovator.command;
 public class ContentCreatorCmd {
        public String parentCode;
        public String content;
        public String locale;
        public Boolean label;
        public Boolean tip; 
        public Boolean placeholder;
        public String labelValue;
        public String tipValue; 
        public String placeholderValue;
        
        public ContentCreatorCmd() {
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public Boolean getLabel() {
            return label;
        }

        public void setLabel(Boolean label) {
            this.label = label;
        }

        public Boolean getTip() {
            return tip;
        }

        public void setTip(Boolean tip) {
            this.tip = tip;
        }

        public Boolean getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(Boolean placeholder) {
            this.placeholder = placeholder;
        }

        public String getLabelValue() {
            return labelValue;
        }

        public void setLabelValue(String labelValue) {
            this.labelValue = labelValue;
        }

        public String getTipValue() {
            return tipValue;
        }

        public void setTipValue(String tipValue) {
            this.tipValue = tipValue;
        }

        public String getPlaceholderValue() {
            return placeholderValue;
        }

        public void setPlaceholderValue(String placeholderValue) {
            this.placeholderValue = placeholderValue;
        }
 
    }