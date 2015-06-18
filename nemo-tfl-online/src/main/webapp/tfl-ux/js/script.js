/* HTML, CSS and JS Templates for Oyster Online 
 Build v0.6.2 on 16-05-2014@04:05  by Creative*/

(function ($) {
    oyster = window.oyster || {};
    oyster.core = {
        contentEditableValues: {
            hasNicknameLink: $('<span class="edit-link-container"><span class="open-brace">(</span><a href="#"" class="edit-value-link">Edit</a><span class="close-brace">)</span></span>'),
            noNicknameLink: $('<span class="edit-link-container"><span class="open-brace"></span><a href="#""  class="edit-value-link unedited">Enter nickname</a><span class="close-brace"></span></span>'),
            boundValue: $('<div class="receipt last-child-border"><span class="info-line"><span class="label">Balance remaining</span><span class="info right-aligned" id="balance-remaining">£30.00</span></span></div>'),
            /**
             contentEditableValues.editContentEditableValue change the text and submit the form
             @params
             contentEditableElement the editable text

             */
            editContentEditableValue: function (contantEditableElement) {
                //get the content editable value and it's parent, prepare the form and get the link to change the text
                var contantEditableElementJQ = $(contantEditableElement),
                    nicknameinputId = contantEditableElementJQ.data('contenteditableinput'),
                    nameInputField = $('#' + nicknameinputId),
                    serializedForm = '',
                    nicknameValue = contantEditableElementJQ.text(),

                    form = nameInputField.parents('form');
                nameInputField.val(nicknameValue);

                //validate?
                oyster.core.formsCommon.successfullSubmitFunctions.submitForm(form);
                contantEditableElementJQ.attr('contenteditable', 'false');
            },

            /**
             contentEditableValues.updateLinkToContentEditableValue changes the default 'provide nickname' text to 'edit nickname'
             @params
             form with the content editable span in

             */
            updateLinkToContentEditableValue: function (form) {
                var contentEditableElement = form.find('[data-contenteditableinput]').first(),
                    linkContainer = contentEditableElement.next('.edit-link-container');
                linkContainer.find('.open-brace').text('(');
                linkContainer.find('.close-brace').text(')');
                linkContainer.find('a').text('Edit');
                contentEditableElement.removeClass('unedited');
            },

            /**
             contentEditableValues.catchEnterForBlur turns return into a form submit for your value
             @params
             e the enter press event

             */
            catchEnterForBlur: function (e) {
                // ENTER PRESSED
                if (e.keyCode == 13) {
                    $(this).blur();
                }
            },

            /**
             contentEditableValues.insertBoundValue used the data returned from the server to update the bound value
             @params
             data : the data returned from the server

             */
            insertBoundValue: function (form, data) {
                //get the form element which passed in the value
                var selector = '#' + form.find('.data-binding-container').first().data('master-id'),
                    bindingElement = $(selector),
                //get the element to update
                    slavedElement = $('#' + bindingElement.data('bind-to')),
                    overwriteControl = $('#' + bindingElement.data('overwrite-control'));
                //check whether we are overwriting

                if (data.overwriteValue) {
                    bindingElement.val(data.originalValue);
                    bindingElement.prop('disabled', true);
                }
                else {
                    bindingElement.prop('disabled', false);
                }
                slavedElement.text(data.newValue);
                form.find('input[name="submittingBoundValueUpdate"]').val(false);
                if (bindingElement.val() === '') {
                    bindingElement.val(0);
                }

            },
            /**
             contentEditableValues.updateBoundElement changes the value of the bound element to the value of the element it is bound to
             */
            updateBoundElement: function (bluredElement) {
                var form = bluredElement.closest('form'),
                    serializedForm = '',
                    submitDifferentiatingInput = form.find('input[name="submittingBoundValueUpdate"]').val(true);

                serializedForm = form.serialize();
                oyster.core.formsCommon.successfullSubmitFunctions.submitForm(form);
            },

            /**
             contentEditableValues.init sets up the content editable spans - adds links to edit them, sets up the form inputs with the default value,
             sets up the event handellers. Also sets up the data binding elements.
             */
            init: function () {
                var contentEditableElements = $('.content-editable-element'),
                    dataBoundElements = $('[data-value-binding]'),
                    currentValue = '',
                    currentElement = '',
                    matchedInput = '',
                    matchedInputId = '',
                    slavedElement = '',
                    bluredElement = '',
                    overwriteControls = [];
                for (var i = contentEditableElements.length - 1; i >= 0; i--) {
                    currentElement = $(contentEditableElements[i]);
                    //test whether we already have a value
                    matchedInputId = currentElement.data('contenteditableinput');
                    matchedInput = $('#' + matchedInputId);
                    currentValue = matchedInput.val();
                    if (currentValue !== "") {
                        currentElement.after(oyster.core.contentEditableValues.hasNicknameLink);
                    }
                    else {
                        currentElement.text('').after(oyster.core.contentEditableValues.noNicknameLink);
                    }

                    currentElement.on("keypress", oyster.core.contentEditableValues.catchEnterForBlur);
                }
                for (var j = dataBoundElements.length - 1; j >= 0; j--) {
                    currentElement = $(dataBoundElements[j]);
                    //add the element bound to this - js only functionality
                    currentElement.parents('.data-binding-container').append(oyster.core.contentEditableValues.boundValue);
                }
                dataBoundElements.on('blur', function (e) {
                    e.stopPropagation();
                    oyster.core.contentEditableValues.updateBoundElement($(this));
                });
                $('.overwrite-control').on('change', function () {
                    if (!this.checked) {
                        $('#' + $(this).data('overwrites')).val(0);
                    }
                    oyster.core.contentEditableValues.updateBoundElement($(this));
                });

                $('.edit-value-link').on('click', function (e) {
                    e.stopPropagation();
                    var contentEditableElement = $(this).parents('.info-line').find('.content-editable-element');
                    contentEditableElement.attr('contenteditable', 'true').text('').focus();
                });
                $('.content-editable-element').on('blur', function () {
                    oyster.core.contentEditableValues.editContentEditableValue(this);
                });
            }
        },
        formsCommon: {
            errorMessageLines: '<span class="error-message-line"></span>',
            errorMessageContainer: '<div class="error-message message-wrapper"><span class="field-validation-error"></span></div>',
            formStepData: {},
            successfullSubmitFunctions: {},
            /**
             formsCommon.setupMultistepForms sets up forms which have multiple submits
             */
            setupMultistepForms: function () {
                var form = $('.multi-step-form');
                form.on('submit', function (e) {
                    //first test for whether this is the last step
                    var form = $(this),
                        formId = form.attr('id'),
                        currentStep = form.data('current-step'),
                        numberOfSteps = form.data('number-of-steps');

                    if (currentStep != numberOfSteps) {
                        e.preventDefault();
                        //submit form
                        //validate?
                        oyster.core.formsCommon.successfullSubmitFunctions.submitForm(form);
                    }
                    else {
                        if (oyster.core.formsCommon.formStepData[formId][numberOfSteps] !== undefined) {
                            oyster.core.formsCommon.formStepData[formId][numberOfSteps]();
                        }
                    }

                });
                form.find('.oo-back-button').data('act-as-link', false).on('click', function (e) {
                    var link = $(this);
                    if (!link.data('act-as-link')) {
                        e.preventDefault();
                        //oyster.core.registerCard.showNumberInput();
                        oyster.core.formsCommon.goToPreviousStep($(this).parents('.multi-step-form'));
                    }
                });
            },
            /**
             formsCommon.goToNextStep

             */
            goToNextStep: function (form, data) {
                var nextStep = form.data('current-step') + 1,
                    numberOfSteps = form.data('number-of-steps'),
                    isLastStep = nextStep == numberOfSteps;
                //update which step we are on
                form.data('current-step', nextStep);

                if (isLastStep) {
                    form.find('.primary-button').val('Continue');
                }
                //(TODO:task1 - will need to add code here to remove any errors that were dynamically inserted)
                oyster.core.formsCommon.removeErrors();
                oyster.core.formsCommon.goToStep(form, nextStep, data);
            },
            /**
             formsCommon.goToPreviousStep

             */
            goToPreviousStep: function (form) {
                var currentStep = form.data('current-step'),
                    nextStep = currentStep - 1,
                    isFirstStep = currentStep == 1;
                //update which step we are on
                form.data('current-step', nextStep);
                //update the back button
                oyster.core.formsCommon.updateBackButton(form, isFirstStep);
                //no data
                oyster.core.formsCommon.goToStep(form, nextStep, {});
            },
            /**
             formsCommon.goToStep

             */
            goToStep: function (form, step, data) {
                oyster.core.formsCommon.formStepData[form.attr('id')][step - 1](form, data);
            },

            /*
             formsCommon.updateBackButton converts between the back button taking you back a page,
             and taking you to previous form step
             */
            updateBackButton: function (form, isFirstStep) {
                if (!isFirstStep) {
                    //make go back a step when clicked
                    $('.oo-back-button').data('act-as-link', false);
                }
                else {
                    //make follow link when clicked
                    $('.oo-back-button').data('act-as-link', true);
                }
            },
            /**
             formsCommon.removeErrors takes the line items which are errors and deletes them

             */
            removeErrors: function () {
                var topLevelContainer = $('.top-level-message-container');
                topLevelContainer.find('.error-message-line').remove();
                topLevelContainer.hide();
            },
            /**
             formsCommon.insertErrors takes the line items which are errors and inserts them at the top of the page

             */
            insertErrors: function (errorMessages) {
                var errorTopLevelMessageContainer = $('.top-level-message-container'),
                    errorMessageContainer = errorTopLevelMessageContainer.find('.field-validation-error'),
                    messages = errorMessages,
                    message = '',
                    messageFor = '',
                    forInput = '';
                for (var i = messages.length - 1; i >= 0; i--) {
                    message = messages[i].message;
                    messageFor = messages[i].forInput;
                    errorTopLevelMessageContainer.show();
                    var nextErrorContainer = $('<div/>').html(oyster.core.formsCommon.errorMessageLines).contents();
                    nextErrorContainer.text(message);
                    errorMessageContainer.append(nextErrorContainer);
                    forInput = $('#' + messageFor);
                    if (forInput.length > 0) {
                        ////(TODO:task1 -insert function to put messages in)
                    }
                }
            },
            setUpTopLevelErrorContainer: function () {
                $('.top-level-message-container').append(oyster.core.formsCommon.errorMessageContainer).hide();
            },
            init: function () {
                oyster.core.formsCommon.setUpTopLevelErrorContainer();
                oyster.core.formsCommon.setupMultistepForms();
                $('input[name="jsEnabled"]').val('true');

            }
        },
        registerCard: {
            securityInput: '<div class="csc-module oyster-security-input"><div class="form-control-wrap text-input"><label for="security-input" class="emphasis">A memorable place of yours?</label><div class="form-control"><input type="text" name="security-input" id="security-input" class="shaded-input"></div></div></div>',
            stepTwoReminderContent: '<p>You can use your current Oyster card until you touch in at an activation station with this new one.</p><p>Once you\'ve touched in, your original card will be cancelled and you won\'t be able to use it anymore.</p>',
            stepOneReminderContent: '<p>By selecting \'Add this card\' I confirm I have read, understood and accept the <a href=\'#\'>terms and conditions</a> of TfL\'s Oyster and Contactless website.<p>',
            multistepFormSteps: {
                registerCardForm: {
                    /*
                     registerCardForm.setupStepOne shows the number input,hides the security input and updates
                     the data attribute on the form to say we have not submitted number, changes the card shown back to red
                     **/
                    setupStepOne: function (form, data) {
                        var container = form.find('.form-input-container');
                        //show number input
                        container.find('.oyster-number-input-container').show();
                        //hide security input
                        var securityInput = container.find('.oyster-security-input');
                        if (securityInput.length !== 0) {
                            securityInput.hide();
                        }
                        container.find('.highlight-number').text('');
                        //update form to say we have not submitted number
                        form.data('submitted-number', 'false');
                        //set the input which differentiates between submits
                        form.find('input[name="checkForSecurityQuestion"]').val('true');

                        form.find('.reminder-container .content').html(oyster.core.registerCard.stepOneReminderContent);
                        $('.registered-card').removeClass('registered-card').addClass('unregistered-card');
                    },
                    /*
                     registerCardForm.setupStepTwo shows the number input,hides the security input and updates
                     the data attribute on the form to say we have not submitted number
                     **/
                    setupStepTwo: function (form, data) {
                        var cardNumber = String(data.cardNumber),
                            position = cardNumber.length - 2,
                            formattedNumber = [cardNumber.slice(0, position), ' ', cardNumber.slice(position)].join('');
                        form.find('.highlight-number').text(formattedNumber);
                        form.find('.unregistered-card').removeClass('unregistered-card').addClass('registered-card');
                        form.find('.oyster-number-input-container').hide();
                        form.find('.reminder-container .content').html(oyster.core.registerCard.stepTwoReminderContent);
                        form.find('input[name="checkForSecurityQuestion"]').val('false');
                        if (data.showSecurityQuestion) {
                            oyster.core.registerCard.insertSecurityInput(form.find('.form-input-container'));
                        }
                    }
                }
            },

            /**
             registerCard.insertSecurityInput inserts the form elements for the security question
             */
            insertSecurityInput: function (container) {
                var securityInputContainer = container.find('.oyster-security-input');
                if (securityInputContainer.length === 0) {
                    container.append(oyster.core.registerCard.securityInput);
                }
                else {
                    securityInputContainer.show();
                }
            },

            /**
             registerCard.insertConfirmation reveals the other picture also inserts number, and marks the form as having added the number,
             ready for the next submit to complete the process

             */
            insertConfirmation: function (form, data) {
                oyster.core.registerCard.updateBackButton(form);
            },
            init: function () {

            }
        },
        productsOnCard: {
            productAddedContainer: '<div class="product-added receipt"><h4 class="receipt-heading"></h4><a href="#" class="remove-product"><span class="icon close-icon hide-text">Remove product from oyster card</span></a></span></div>',
            productAddedInfoLine: '<span class="info-line"><span class="label"></span><span class="info price right-aligned"></span>',
            revealProductLink: '<a href="#" class="container-dotted-border reveal-next-product add-something-link"><div><span class="centered-icon-wrapper"><span class="icon plus-icon"></span>Add Another travel card</span></div></a>',

            /**
             productsOnCard.addProductToCard adds the container for confirmed added product.
             */
            addProductToCard: function (form) {
                //submit form
                //validate?
                oyster.core.formsCommon.successfullSubmitFunctions.submitForm(form);

            },


            /**
             productsOnCard.insertConfirmation takes the line items
             and price that have come back from the server and inserts them

             */
            insertConfirmation: function (form, data) {
                var products = data.productsAdded,
                    price = '',
                    label = '',
                    heading = data.heading,
                    productSelector = data.productSelector,
                    productGroup = form.parents('.product-group').first(),
                    confirmationContainer = form.find('.confirmation');
                //create container and insert information
                container = $('<div/>').html(oyster.core.productsOnCard.productAddedContainer).contents();

                container.find('.receipt-heading').text(heading);
                //tag the link with the form it is associated with
                container.find('.remove-product').data('form-id', form.attr('id'));
                confirmationContainer.append($(container));
                //insert the information
                for (var i = 0; i < products.length; i++) {
                    //get product information
                    price = products[i].price;
                    label = products[i].label;

                    var lineContainer = $('<div/>').html(oyster.core.productsOnCard.productAddedInfoLine).contents();
                    lineContainer.find('.label').text(label);
                    lineContainer.find('.price').text(price);
                    container.append(lineContainer);
                }

                //hide the checkbox and the form
                productGroup.children('input, label').hide();
                form.find('input[name="added-to-card"]').val(true);
                oyster.core.productsOnCard.hideProductForm(form);

                //find out how many products cards now added
                var travelCardsAdded = oyster.core.productsOnCard.getNumProductsAdded(productGroup);

                //if we can add more, add a link
                if (travelCardsAdded < 3) {
                    oyster.core.productsOnCard.updateProductLink(productSelector, productGroup);
                }
                //productGroup.removeClass('expanded');
                ////(TODO:task1 -insert function to remove error messages)

            },

            /**
             productsOnCard.showProductForm shows the form and updates the hidden input
             which is used to determine whether to show the product form/add product button

             */
            showProductForm: function (form) {
                form.find('input[name="show-product"]').val('true');
                form.find('.product-inputs').show();
            },
            /**
             productsOnCard.hideProductForm hides the form and updates the hidden input
             which is used to determine whether to show the product form/add product button

             */
            hideProductForm: function (form) {
                form.find('input[name="show-product"]').val('false');
                form.find('.product-inputs').hide();
            },
            /**
             productsOnCard.getNumProductsShown returns the number of products in a particular group
             that have forms being shown (this should be 1 or 0)

             */
            getNumProductsShown: function (productGroup) {
                var count = 0,
                    hiddenInputs = productGroup.find('input[name="show-product"]');
                for (var i = hiddenInputs.length - 1; i >= 0; i--) {
                    if ($(hiddenInputs[i]).val() == 'true') {
                        count++;
                    }
                }
                return count;
            },
            /**
             productsOnCard.getNumProductsAdded returns the number of products in a particular group
             that have been added

             */
            getNumProductsAdded: function (productGroup) {
                var count = 0,
                    hiddenInputs = productGroup.find('input[name="added-to-card"]');
                for (var i = hiddenInputs.length - 1; i >= 0; i--) {
                    if ($(hiddenInputs[i]).val() == 'true') {
                        count++;
                    }
                }
                return count;
            },

            initCalendars: function () {
                var calendarWrappers = $('.fc-calendar-wrapper');
                if (calendarWrappers.length > 0) {
                    /*					$('.oo-date-picker').on('focus',function(){
                     var input = $(this),
                     calendarWrapper = input.parents('.input-with-calendar-container').find('.fc-calendar-wrapper');
                     oyster.core.productsOnCard.toggleCalendar($(this));
                     calendarWrapper.attr('tabindex',0).focus().on('blur',function(e){
                     console.log(e);
                     e.stopPropagation();
                     oyster.core.productsOnCard.toggleCalendar($(this));
                     $(this).off('blur');
                     });
                     });*/
                    $('.fc-today').addClass('fc-selected');
                    //$('.fc-row > div').addClass('cell');
                    $('.input-with-calendar-container').on('click', '.fc-row > div', function () {
                        oyster.core.productsOnCard.selectDate($(this));

                    })
                        .on('mouseenter', '.fc-row > div', function () {
                            $(this).addClass('highlighted-day');
                        })
                        .on('mouseleave', '.fc-row > div', function () {
                            $(this).removeClass('highlighted-day');
                        });

                }
            },

            selectDate: function (cell) {
                var day = cell.children('.fc-date'),
                    calendar = day.parents('.fc-calendar-wrapper'),
                    month = calendar.find('.calendar-month'),
                    year = calendar.find('.calendar-year'),
                    input = calendar.parents('.input-with-calendar-container').find('input');
                calendar.find('.fc-selected').removeClass('fc-selected');
                cell.addClass('fc-selected');
                var selectedDate = oyster.core.productsOnCard.getDateFromCalendar(calendar),
                    dateString = selectedDate.day + " " + selectedDate.month + " " + selectedDate.year;
                input.val(oyster.core.productsOnCard.parseDate(dateString));
            },

            toggleCalendar: function (input) {
                input.parents('.input-with-calendar-container').find('a.form-element-accordian-control').click();
            },
            /**
             productsOnCard.parseDate gets data as dd/mm/yyyy
             */
            parseDate: function (dateString) {
                var date = new Date(dateString);
                var dd = date.getDate();
                var mm = date.getMonth() + 1; //January is 0!
                var yyyy = date.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd;
                }
                if (mm < 10) {
                    mm = '0' + mm;
                }
                return dd + '/' + mm + '/' + yyyy;
            },
            /**
             productsOnCard.getDateFromCalendar returns an object with the current date of the calendar in.
             */
            getDateFromCalendar: function (calendar) {
                var month = calendar.find('.calendar-month').text(),
                    year = calendar.find('.calendar-year').text(),
                    day = calendar.find('.fc-selected .fc-date').text();

                return {
                    'day': day,
                    'month': month,
                    'year': year
                };
            },

            /**
             productsOnCard.getNextSlot returns the id of the next free travelcard slot
             */
            getNextSlot: function (productSelector) {
                var productSlots = $('.' + productSelector);
                for (var i = 0; i < productSlots.length; i++) {
                    nextSlot = $(productSlots[i]).find('input[name="added-to-card"]');
                    if (nextSlot.val() == "false") {
                        return '#' + nextSlot.parents('form').attr('id');
                    }
                }
            },
            /**
             productsOnCard.getRevealProductLink returns a link which will reveal the next travelcard.
             */
            getRevealProductLink: function (productSelector) {
                var link = $('<div/>').html(oyster.core.productsOnCard.revealProductLink).contents(),
                    selectorToReveal = oyster.core.productsOnCard.getNextSlot(productSelector);
                link.data('reveal-product', selectorToReveal);
                link.on('click', function (e) {
                    e.preventDefault();
                    oyster.core.productsOnCard.showTravelCardClickHandeller($(this));
                });
                return link;
            },
            /**
             productsOnCard.updateProductLink updates the link which will reveal the next travelcard.
             */
            updateProductLink: function (productSelector, productGroup) {
                if (productSelector === 'travel-card') {
                    var showNextLink = $('.reveal-next-product');
                    if (showNextLink.length === 0) {
                        //if we have not added link yet
                        //insert 'add another' link
                        var link = oyster.core.productsOnCard.getRevealProductLink(productSelector);
                        link.appendTo(productGroup);
                    }
                    else {
                        selectorToReveal = oyster.core.productsOnCard.getNextSlot(productSelector);
                        showNextLink.data('reveal-product', selectorToReveal);
                        showNextLink.on('click', function (e) {
                            e.preventDefault();
                            oyster.core.productsOnCard.showTravelCardClickHandeller($(this));
                        });
                        showNextLink.show();
                    }
                }
            },

            /**
             productsOnCard.showTravelCardClickHandeller link to show the next travel card.
             shows the form and opens the container.
             */
            showTravelCardClickHandeller: function (link) {
                var selectorToShow = link.data('reveal-product'),
                    form = $(selectorToShow);
                oyster.core.productsOnCard.showProductForm(form);
                //form.parents('.accordian-input-container').addClass('expanded');
                link.hide();
            },

            /**
             productsOnCard.removeProductFromCard successfull removal of a product handeller
             */
            removeProductFromCard: function (form, data) {
                oyster.core.productsOnCard.removeConfirmation(form, data);
            },
            /**
             productsOnCard.removeConfirmation set hidden input back to false to indicate product not added.
             see how many forms on display - should have either a confirmation
             or a form with checkbox on display at all times
             */
            removeConfirmation: function (form, data) {
                var productsRemoved = data.productsRemoved;
                for (var j = productsRemoved.length - 1; j >= 0; j--) {
                    var removedProduct = productsRemoved[j],
                        productSelector = removedProduct.productSelector,
                        productGroup = form.parents('.product-group').first(),
                        hiddenInput = form.find('input[name="added-to-card"]');
                    hiddenInput.val('false');
                    oyster.core.productsOnCard.updateProductLink(productSelector, productGroup);

                    //delete the confirmation
                    var removingLinks = $('.remove-product');
                    for (var i = removingLinks.length - 1; i >= 0; i--) {
                        var nextLink = $(removingLinks[i]),
                            linkData = nextLink.data('formId');
                        if (linkData == form.attr('id')) {
                            nextLink.parents('.product-added').remove();
                        }
                    }
                    //update the form to say this product is no longer added to the card
                    hiddenInput.val(false);
                    //find out if we need to show a form again - only if no forms being shown
                    //and no products added
                    var numProductsShown = oyster.core.productsOnCard.getNumProductsShown(productGroup);
                    var numProductsAdded = oyster.core.productsOnCard.getNumProductsAdded(productGroup);
                    if (numProductsShown === 0 && numProductsAdded === 0) {
                        oyster.core.productsOnCard.showProductForm(form);
                        //expand the box
                        //productGroup.addClass('expanded');

                    }
                    if (numProductsAdded === 0) {
                        //show first form and input and label
                        productGroup.children('input, label').show();
                    }
                    if (productSelector === 'travel-card') {
                        if (numProductsAdded === 0 || numProductsShown > 0) {
                            //hide the button to add another travel card
                            $('.reveal-next-product').hide();
                        }
                    }
                }
            },
            /**
             productsOnCard.setUpProducts hide products we don't need to see yet, catch form submissions,
             set up remove product click handellers
             */
            setUpProducts: function () {
                var productGroups = $('.product-group'),
                    productDisplayIndicators = $('input[name="show-product"]'),
                    hiddenInput = '',
                    productGroup;
                for (var i = productDisplayIndicators.length - 1; i >= 0; i--) {
                    hiddenInput = $(productDisplayIndicators[i]);
                    if (hiddenInput.val() == "false") {
                        oyster.core.productsOnCard.hideProductForm(hiddenInput.parents('form'));
                    }
                }

                $('.product').on('submit', function (e) {
                    e.preventDefault();
                    oyster.core.productsOnCard.addProductToCard($(this));
                });
                $('.accordian-input-container').on('click', '.remove-product', function (e) {
                    e.preventDefault();
                    oyster.core.formsCommon.successfullSubmitFunctions.submitForm($(this).parents('form'));

                });
            },

            init: function () {

                oyster.core.productsOnCard.initCalendars();
                oyster.core.productsOnCard.setUpProducts();


            }
        },
        tableFilters: {
            init: function () {

                $('.table-filters select').on('change', function () {
                    var select = $(this),
                        form = select.parents('.oo-responsive-form'),
                        extraOptionsOptionVal = select.data('val-for-extra-options'),
                        extraOptionsOption = '';

                    if (extraOptionsOptionVal !== undefined) {
                        if (select.val() != extraOptionsOptionVal) {
                            form.submit();
                        }
                    }
                    else {
                        form.submit();
                    }
                });
            }

        },
        bacsSubmits: {
            stepOneReminderContent: 'Please remember to double check the information you have provided.',
            stepTwoReminderContent: 'Please double check your account details.',
            webCreditBalance: {
                multistepFormSteps: {
                    setupStepOne: function (form, data) {
                        $('form .dynamic-content input , form .multistep-fields input').prop('disabled', false);
                        $('.reminder-container .content').text(oyster.core.bacsSubmits.stepOneReminderContent);
                        form.find('input[name="doubleCheckShown"]').val('false');
                    },
                    setupStepTwo: function (form, data) {
                        $('form .dynamic-content input , form .multistep-fields input').prop('disabled', true);
                        $('.reminder-container .content').text(oyster.core.bacsSubmits.stepTwoReminderContent);
                        form.find('input[name="doubleCheckShown"]').val('true');
                    },
                    onSubmit: function (form, data) {
                        $('form .dynamic-content input , form .multistep-fields input').prop('disabled', false);
                    }
                }
            }
        },
        customInputs: {
            /**
             customInputs.init sets up the custom input styling and the input accordians.
             lots of code taken from csc/nwp because of class clashes with those
             two projects using the same selector - functions were getting run from both libraries.
             */
            init: function () {
                oyster.core.customInputs.setupInputAccordians();
                oyster.core.customInputs.toggleExtraOptions();
            },
            /**
             customInputs.setupInputAccordians closes the appropriate extra content boxes and sets up event handellers
             */
            setupInputAccordians: function () {
                var extraContentInputLists = $(".input-list.with-extra-content input[checked='checked']"),
                    input = '';

                for (var i = extraContentInputLists.length - 1; i >= 0; i--) {
                    input = $(extraContentInputLists[i]);
                    oyster.core.customInputs.toggleExtraContent(input.parent('.oo-toggle-content'), input);
                }
                $(".input-list.with-extra-content > .oo-toggle-content > input, .learn-more-toggle").on('click', function () {
                    //show hide extra content
                    var clicked = $(this),
                        input = '',
                        keyContainer = '';

                    if (clicked.hasClass('learn-more-toggle')) {
                        keyContainer = clicked.parents('.oo-toggle-content');
                        input = keyContainer.find('> input');
                        oyster.core.customInputs.toggleExtraContent(keyContainer, input, true);
                    }
                    else {
                        keyContainer = clicked.parents('.oo-toggle-content');
                        oyster.core.customInputs.toggleExtraContent(keyContainer, clicked, false);
                    }
                });


            },

            /**
             customInputs.toggleExtraContent opens the appropriate extra content for an input accordian
             */
            toggleExtraContent: function (keyContainer, input, clickedControl) {
                var type = input.attr('type'),
                    list = keyContainer.parents(".input-list.with-extra-content");
                //for checkboxes we can have as many checked as we like, so checking and unchecking should open and close only itself
                if (type == 'checkbox') {
                    keyContainer.toggleClass('expanded');
                }
                //for radios we only have one checked, so checking another should uncheck the previous
                else if (type == 'radio') {
                    if (clickedControl) {
                        keyContainer.toggleClass('expanded');
                    }
                    else {
                        list.find('.expanded').removeClass('expanded');
                        keyContainer.addClass('expanded');
                    }
                }

            },


            /**
             customInputs.revealExtraOptions change handeller for the form inputs which reveal extra options
             */
            revealExtraOptions: function (changedElement) {
                var selector = changedElement.data('extra-options'),
                    options = $('.' + selector),
                    submitSelector = changedElement.data('submit-button'),
                    submitButton = '',
                    hasSubmit = false;
                if (submitSelector !== undefined && submitSelector.length > 0) {
                    submitButton = $('.' + submitSelector);
                    hasSubmit = true;
                }
                if (changedElement.val() == changedElement.data('val-for-extra-options')) {
                    options.show();
                    if (hasSubmit) {
                        submitButton.show();
                    }
                }
                else {
                    options.hide();
                    options.find('input').val('');
                    if (hasSubmit) {
                        submitButton.hide();
                    }
                }
            },

            toggleExtraOptions: function () {
                var elementsWithExtraOptions = $('[data-extra-options]'),
                    elementWithExtraOptions = '',
                    selector = '',
                    options = '';
                for (var i = elementsWithExtraOptions.length - 1; i >= 0; i--) {
                    elementWithExtraOptions = $(elementsWithExtraOptions[i]);
                    selector = elementWithExtraOptions.data('extra-options');
                    $('.' + selector).hide().addClass('extra-form-options');
                }
            }
        },
        /**
         oyster.core.init run all inits
         */
        init: function () {
            oyster.core.contentEditableValues.init();
            oyster.core.productsOnCard.init();
            oyster.core.customInputs.init();
            oyster.core.registerCard.init();
            oyster.core.formsCommon.init();
            oyster.core.tableFilters.init();

            //disable inert links
            $('.cancelled-card a.status-anchor').on('click', function (e) {
                e.preventDefault();
            });
            $('body').on('change', '[data-extra-options]', function () {
                oyster.core.customInputs.revealExtraOptions($(this));
            });
        }
    };

    oyster.core.init();

})(jQuery);

