/* HTML, CSS and JS Templates for Oyster Online 
 Build v0.6.2 on 16-05-2014@04:05  by Creative*/
(function ($) {
    oyster = window.oyster || {};
    oyster.development = true;
    oyster.config = {
        forms: {
            /*
             form subbmitted to update the nickname of a given oyster card.
             */
            nicknameYourOysterCard: {
                'url': 'assets/oyster/stubs/contentEditable.php',
                'onSubmit': 'content-editable-value'
            },
            /**
             form submitted to refund web credits - this is submitted in several instances.
             1) From blurring the web credit input (to update the remaining balance dynamically - an input
             with name="submittingBoundValueUpdate" differentiates between this and an 'account details entered' submit).
             2) First step of full submission - before the check our details message is shown (may not need to be,
             but followed the pattern for the register card of submitting the form each time. an input
             with name="doubleCheckShown" differentiates whether this is the 'check step' or 'submit step')
             3) When the user has checked the details and pressed submit again, there is an 'onSubmit' function to
             reactivates the inputs, then the default submission takes over.
             */
            refundWebCredits: {
                'url': 'assets/oyster/stubs/generic.php',
                'onSubmit': 'refund-web-credits-multi-step-form'
            },
            /**
             form submitted in several steps:
             1) When the user puts in a card number (to see if they also need to put in a security question
             an input with name="checkForSecurityQuestion" differentiates between steps)
             2) Once the user has checked the number input and (optionally) entered the security question
             */
            registerCard: {
                'url': 'assets/oyster/stubs/generic.php',
                'onSubmit': 'register-card-multistep-form'
            },
            /**
             This form is submitted to add and remove a product - an input with name='added-to-card' differentiates whether the
             product should be added or removed (if it is already added, it should be removed).
             */
            addRemoveProduct: {
                'url': 'assets/oyster/stubs/generic.php',
                'onSubmit': 'add-remove-product'
            }

        }
    };

    //list of steps for the registerCard multistep form
    oyster.core.formsCommon.formStepData['register-oyster'] = [oyster.core.registerCard.multistepFormSteps.registerCardForm.setupStepOne, oyster.core.registerCard.multistepFormSteps.registerCardForm.setupStepTwo];
    //list of steps for the refundWebCredits multistep form
    oyster.core.formsCommon.formStepData['balance-update'] = [oyster.core.bacsSubmits.webCreditBalance.multistepFormSteps.setupStepOne, oyster.core.bacsSubmits.webCreditBalance.multistepFormSteps.setupStepTwo, oyster.core.bacsSubmits.webCreditBalance.multistepFormSteps.onSubmit];

    /**
     all form submission for the special forms listed above go through this function
     */
    oyster.core.formsCommon.successfullSubmitFunctions['submitForm'] = function (form) {
        //serialize the form
        var serializedForm = form.serialize();
        $.ajax({
            type: "POST",
            url: oyster.config.forms[form.data('submit-label')].url,
            data: serializedForm,
            dataType: "json",
            success: function (data) {
                oyster.core.formsCommon.removeErrors();
                var errorMessages = data['errorMessages'];
                if (errorMessages != undefined && errorMessages.length > 0) {
                    oyster.core.formsCommon.insertErrors(errorMessages);
                }
                else {
                    var submitFunction = oyster.config.forms[form.data('submit-label')].onSubmit;
                    oyster.core.formsCommon.successfullSubmitFunctions[submitFunction](form, data);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
                /*
                 Generic error message for failure to submit form - placeholder for whatever error handelling is appropriate
                 */
                /*  var errorMessages= [
                 {
                 'forInput':'nickname',
                 'message':'Sorry, your form could not be submitted at this time, please try again later.'
                 }
                 ];
                 oyster.core.formsCommon.removeErrors();
                 oyster.core.formsCommon.insertErrors(errorMessages);*/
                /*
                 TODO: delete this - used for demo purposes in prototype only
                 */
                var submitFunction = oyster.config.forms[form.data('submit-label')].onSubmit;
                oyster.core.formsCommon.successfullSubmitFunctions[submitFunction](form, {});
            }
        });

    };

    /**
     when the addRemoveProduct form is successfully submitted, this function is called.
     Depending on whether the form added or removed a product, it either shows a confirmation
     message or removes one and shows the product form
     */
    oyster.core.formsCommon.successfullSubmitFunctions['add-remove-product'] = function (form, data) {
        var productAddedIndicator = form.find('input[name="added-to-card"]');
        if (productAddedIndicator.val() == "true") {
            //remove the product
            //TODO: remove test data
            var data = {};
            if (form.attr('id') == 'payg-topup-autotopup') {
                data = {
                    productsRemoved: [
                        {
                            heading: "Pay as you go / Auto top-up",
                            productSelector: "payg-topup",
                            price: "£200.00",
                            label: "1 month, Zone 1-3"
                        }
                    ]
                };
            }
            else if (form.attr('id') == 'tram') {
                data = {
                    productsRemoved: [
                        {
                            heading: "Annual bus and Tram",
                            price: "£30.00",
                            label: "Bus",
                            productSelector: "bus-and-tram"
                        },
                        {
                            heading: "Annual bus and Tram",
                            price: "£20.00",
                            label: "Tram",
                            productSelector: "bus-and-tram"
                        }

                    ]
                };
            }
            else {
                data = {
                    productsRemoved: [
                        {
                            heading: "Travel card",
                            price: "£200.00",
                            label: "1 month, Zone 1-3",
                            productSelector: "travel-card"
                        }

                    ]
                };
            }

            oyster.core.productsOnCard.removeProductFromCard(form, data);
        }
        else {
            //add the product
            //TODO: remove test data
            var showError = (Math.random() < 0.5),
                data = {};

            if (showError) {
                var splitId = form.attr('id').split('-'),
                    length = splitId.length,
                    idIndex = splitId[length - 1],

                    errorMessages = [
                        {
                            'forInput': 'To_zone_' + idIndex,
                            'message': 'Cannot select travel card from zone 1 to zone 1'
                        },
                        {
                            'forInput': 'topcontainer',
                            'message': 'Cannot select two travel cards with the some zones'
                        }
                    ];
                oyster.core.formsCommon.insertErrors(errorMessages);
            }
            else {
                if (form.attr('id') == 'payg-topup-autotopup') {
                    data = {
                        heading: "Pay as you go / Auto top-up",
                        productSelector: "payg-topup",
                        productsAdded: [
                            {
                                "price": "£20.00",
                                "label": "Auto top-up"
                            },
                            {
                                "price": "£30.00",
                                "label": "Pay as you go"
                            }

                        ]
                    };
                }
                else {
                    data = {
                        heading: "Travel card",
                        productSelector: "travel-card",
                        productsAdded: [
                            {
                                "price": "£200.00",
                                "label": "1 month, Zone 1-3"
                            }
                        ]
                    };
                }

                oyster.core.formsCommon.removeErrors();
                oyster.core.productsOnCard.insertConfirmation(form, data);
            }
        }
    };

    /**
     on successfull submission of the registerCard form, this calls the next step.
     step1: submits the entered card number. need to return whether a security question is required.
     step2: submits the number and security question
     */
    oyster.core.formsCommon.successfullSubmitFunctions['register-card-multistep-form'] = function (form, data) {
        //TODO: remove test data
        var data = {},
            showError = form.find('#oyster-number-input').val() === '';
        if (showError) {
            var errorMessages = [
                {
                    'forInput': 'number',
                    'message': 'Sorry, number not recognised'
                },
                {
                    'forInput': 'number',
                    'message': 'must only contain digits 1-9'
                }
            ];
            oyster.core.formsCommon.insertErrors(errorMessages);
        }
        else {
            var showSecurityQuestion = form.find('#oyster-number-input').val() == 1234;
            data = {
                cardNumber: form.find('#oyster-number-input').val(),
                showSecurityQuestion: showSecurityQuestion
            };
            oyster.core.formsCommon.removeErrors();
            oyster.core.formsCommon.goToNextStep(form, data);
        }
    };

    oyster.core.formsCommon.successfullSubmitFunctions['content-editable-value'] = function (form, data) {
        var errorMessages = data['errorMessages'];
        if (errorMessages != undefined && errorMessages.length > 0) {
            form.find('[data-contenteditableinput]').text('Oyster');
        }
        else {
            oyster.core.formsCommon.removeErrors();
            oyster.core.contentEditableValues.updateLinkToContentEditableValue(form);
        }
    };

    /**
     on successfull submission of the refundWebCredits form, this takes the
     appropriate action (depending on whether we are updating the balance/first submission/second submission)
     step1: gets the user to check the input
     step2: submits the account details
     */
    oyster.core.formsCommon.successfullSubmitFunctions['refund-web-credits-multi-step-form'] = function (form, data) {
        if (form.find('input[name="submittingBoundValueUpdate"]').val() == "true") {
            //TODO: remove test data
            var showError = !$('#refundAll')[0].checked && ($('#transfer-ammount').val() > 30),
                data = {};
            if (showError) {
                var errorMessages = [
                    {
                        'forInput': 'transfer-ammount',
                        'message': 'Sorry, that amount is invalid.'
                    }
                ];
                oyster.core.formsCommon.removeErrors();
                oyster.core.formsCommon.insertErrors(errorMessages);
            }
            else {
                if ($('#refundAll')[0].checked) {
                    data = {
                        newValue: '£0.00',
                        inputValue: $('#transfer-ammount').val(),
                        originalValue: 30,
                        overwriteValue: true

                    };
                }
                else {
                    data = {
                        newValue: '£' + (30 - $('#transfer-ammount').val()).toFixed(2),
                        inputValue: $('#transfer-ammount').val(),
                        originalValue: 30,
                        overwriteValue: false

                    };
                }

                oyster.core.formsCommon.removeErrors();
                oyster.core.contentEditableValues.insertBoundValue(form, data);

            }
        }
        else {
            //TODO: remove test data
            showError = form.find('#oyster-number-input').val() === '';
            if (showError) {
                var errorMessages = [
                    {
                        'forInput': 'sort-code',
                        'message': 'Sorry, number not recognised'
                    },
                    {
                        'forInput': 'account-number',
                        'message': 'must only contain digits 1-9'
                    }
                ];
                oyster.core.formsCommon.insertErrors(errorMessages);
            }
            else {
                data = {};
                oyster.core.formsCommon.removeErrors();
                oyster.core.formsCommon.goToNextStep(form, data);
            }
        }
    }
})(jQuery);

