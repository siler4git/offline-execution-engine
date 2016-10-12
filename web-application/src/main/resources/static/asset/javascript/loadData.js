/**
 * Created by leo_zlzhang on 10/10/2016.
 * for jstree and jseditor
 */

var container = document.getElementById('jsoneditor');

var options = {
    mode: 'tree',
    modes: ['code', 'form', 'text', 'tree', 'view'], // allowed modes
    onError: function (err) {
        alert(err.toString());
    },
    onModeChange: function (newMode, oldMode) {
        console.log('Mode switched from', oldMode, 'to', newMode);
    }
};


function loadTestDataFromServer(testCaseName) {
    var json = {};
    $.ajax({
        'url': '/try/api/data/get/byname',
        'data': {
            'name': testCaseName
        },
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            json = data.result;
        },
        error: function (e) {
            console.log("fail to get test data from backend" + e);
        }
    });
    return json;
}


var editor = new JSONEditor(container, options, '');


function loadTreeDataFromServer() {
    var catalog = {};
    $.ajax({
        'url': '/try/api/catalog/load',
        'type': 'get',
        'contentType': "application/json",
        'dataType': 'json',
        'async': false,
        success: function (data) {
            catalog = data.result;
        },
        error: function (e) {
            console.log("fail to get catalog from backend" + e);
        }
    });
    return catalog;
}

var treeData =
    {
        text: "ApiTestData",
        children: [
            {
                text: "advertisement",
                children: [
                    {
                        text: "AdvertisementController",
                        children: [
                            {
                                text: "1_clickAdviertisement",
                                icon: 'jstree-file'
                            }
                        ]
                    }
                ]
            },
            {
                text: "blackword",
                children: [
                    {
                        text: "BlackWordController",
                        children: [
                            {
                                text: "1_checkNameBlockedWord"
                            }
                        ]
                    }
                ]
            },
            {
                text: "E2E",
                children: [
                    {
                        text: "ConsumeGift"
                    },
                    {
                        text: "DiamondVerify"
                    }
                ]
            },
            {
                text: "gift",
                children: [
                    {
                        text: "GiftController",
                        children: [
                            {
                                text: "1_sendgift"
                            },
                            {
                                text: "2_stopPlay"
                            }
                        ]
                    }
                ]
            }
        ]
    }
    ;

$('#tree')
    .on('select_node.jstree', function (e, data) {
        if (data.node.icon === 'jstree-file'){
            console.log('select test case:' + data.node.text);
            editor.set(loadTestDataFromServer(data.node.text));

        }
        var mytree = $.jstree.reference(data.node);
        console.log(mytree.get_json(-1, ['data-title', 'data-link-type', 'id', 'class']));
    })
    .jstree({
        'core': {
            // 'data': loadTreeDataFromServer(),
            'data': treeData,
            "check_callback": true
        },
        "plugins": ["contextmenu"]
    });
