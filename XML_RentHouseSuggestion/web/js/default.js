function displaySearchOptionSpace() {
//    document.getElementById("search_simple").style.height = '40%';
//    document.getElementById("search_simple").style.display = 'none';
//    document.getElementById("search_option").style.display = 'block';
    var heightDiv = document.getElementById("search_space");
    var btnMoreOption = document.getElementById("btn_MoreOption");
    if (heightDiv.style.height === '40%') {
        document.getElementById("search_container").style.height = '650px';
        document.getElementById("search_space").style.height = '90%';
        document.getElementById("div-optional").style.height = '350px';
        document.getElementById("div-optional").style.visibility = 'visible';
        document.getElementById("search_like").style.visibility = 'hidden';
        document.getElementById("label-optional").style.visibility = 'visible';
        document.getElementById("btn_SearchLike").style.visibility = 'hidden';
        document.getElementById("btn_Search").style.visibility = 'visible';
        btnMoreOption.style.background = "url('css/icon_doubleup.png') no-repeat right";
        document.getElementById("search-way").value = 'searchByLocation';

    } else {
        document.getElementById("search_container").style.height = '580px';
        document.getElementById("search_space").style.height = '40%';
        document.getElementById("div-optional").style.height = '0px';
        document.getElementById("div-optional").style.visibility = 'hidden';
        document.getElementById("search_like").style.visibility = 'visible';
        document.getElementById("label-optional").style.visibility = 'hidden';
        document.getElementById("btn_SearchLike").style.visibility = 'visible';
                document.getElementById("btn_Search").style.visibility = 'hidden';
        btnMoreOption.style.background = "url('css/icon_doubledown.png') no-repeat right";
        document.getElementById("search-way").value = 'searchLike';
    }
}

function checkNull() {
    var searchValue = document.getElementById("search_bar").value;
    if (searchValue.trim() === '') {
        document.getElementById("latitude_param").value = '';
        document.getElementById("longitude_param").value = '';
    }

}

var map, marker, infowindow;
var markers = [];
var address_infos = {};

function initAutocomplete() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        center: {lat: 21.0167904, lng: 105.7819856},
        streetViewControl: false,
        mapTypeControl: false
    });

    placeMarker({lat: 21.0167904, lng: 105.7819856});
    geocodeAddress(marker.position);
    map.panTo(marker.position);
    markers.push(marker);

    map.addListener("click", function (e) {
        clearMarkers();
        placeMarker(e.latLng);
        geocodeAddress(e.latLng);
        map.panTo(marker.position);
        markers.push(marker);
    });

    createInfoWindow();

    // Create the search box and link it to the UI element.
    var input = document.getElementById('search_bar');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    // map.addListener('bounds_changed', function() {
    // searchBox.setBounds(map.getBounds());
    // });

    searchBox.addListener("places_changed", function () {
        searchBox.set("map", null);
        clearMarkers();

        var places = searchBox.getPlaces();
        if (places.length == 0) {
            console.log("Returned no place");
            return;
        }

        var bounds = new google.maps.LatLngBounds();

        if (places.length > 1) {
            places.forEach(function (place) {
                if (!place.geometry) {
                    console.log("Returned place contains no geometry");
                    return;
                }

                var icon = {
                    url: place.icon,
                    size: new google.maps.Size(71, 71),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(17, 34),
                    scaledSize: new google.maps.Size(25, 25)
                };

                marker = new google.maps.Marker({
                    map: map,
                    icon: icon,
                    title: place.name,
                    position: place.geometry.location
                });

                marker.bindTo("map", searchBox, "map");
                marker.addListener("map_changed", function () {
                    if (!this.getMap()) {
                        this.unbindAll();
                    }
                });

                if (place.geometry.viewport) {
                    // Only geocodes have viewport.
                    bounds.union(place.geometry.viewport);
                } else {
                    bounds.extend(place.geometry.location);
                }
            });

            map.fitBounds(bounds);
            searchBox.set("map", map);
            map.setZoom(Math.min(map.getZoom(), 15));
            // searchBox.setBounds(map.getBounds());
        } else {
            infowindow.close();
            var place = places[0];
            if (!place.geometry) {
                console.log("Returned place contains no geometry");
                return;
            }

            placeMarker(place.geometry.location);

            marker.bindTo("map", searchBox, "map");
            marker.addListener("map_changed", function () {
                if (!this.getMap()) {
                    this.unbindAll();
                }
            });

            create_address_infos(place);

            infowindow.setContent(
                    "<div>" +
                    "<b>Address :</b> " + place.formatted_address + "<br>" +
                    "<b>Latitude :</b> " + place.geometry.location.lat() + "<br>" +
                    "<b>Longitude :</b> " + place.geometry.location.lng() +
                    "</div>"
                    );

            if (place.geometry.viewport) {
                // Only geocodes have viewport.
                bounds.union(place.geometry.viewport);
            } else {
                bounds.extend(place.geometry.location);
            }

            map.fitBounds(bounds);
            searchBox.set("map", map);
            map.setZoom(Math.min(map.getZoom(), 15));
            infowindow.open(map, marker);
            markers.push(marker);
        }
    });

}

function placeMarker(latLng) {
    marker = new google.maps.Marker({
        position: latLng,
        map: map
    });
}

function createInfoWindow() {
    if (infowindow) {
        infowindow.close();
    }
    infowindow = new google.maps.InfoWindow();
}

function geocodeAddress(latLng) {
    var geocoder = new google.maps.Geocoder;
    createInfoWindow();

    geocoder.geocode(
            {"location": latLng},
            function (results, status) {
                if (status === google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        create_address_infos(results[0]);

                        infowindow.setContent(
                                "<div>" +
                                "<b>Address :</b> " + address_infos["name"] + "<br>" +
                                "<b>Latitude :</b> " + address_infos["latitude"] + "<br>" +
                                "<b>Longitude :</b> " + address_infos["longitude"] +
                                "</div>"
                                );
                        infowindow.open(map, marker);
                    } else {
                        console.log("No results found");
                    }
                } else {
                    console.log("Geocoder failed due to: " + status);
                }
            }
    );
}

function clearMarkers() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null)
    }
    markers = [];
}

function create_address_infos(address) {
    address_infos = {
        name: address.formatted_address.toString(),
        latitude: address.geometry.location.lat(),
        longitude: address.geometry.location.lng(),
        prefecture: "",
        city: "",
        town: "",
        choume: "",
        banchi: "",
        gou: ""
    };
    document.getElementById("latitude_param").value = address.geometry.location.lat();
    document.getElementById("longitude_param").value = address.geometry.location.lng();
}

var regObj;
var xmlDOM = new ActiveXObject("Microsoft.XMLDOM");
function searchProcess() {
    if (!regObj) {
        return false;
    }

    if (regObj) {
        xmlDOM.async = false;
        xmlDOM.loadXML(regObj);
        var list = document.getElementById("listHouse");

        if (xmlDOM.parseError.errorCode != 0) {
            alert("error :" + xmlDoc.parseError.reason);
        } else {
            //delete screen
            while (list.hasChildNodes()) {
                list.removeChild(list.firstChild);
            }
            //find node
            var search = document.getElementById("search_like");
            var searchValue = search.value;
            if (searchValue.trim() !== "") {
                searchNode2(xmlDOM, searchValue);
                //search server
                if (!list.hasChildNodes()) {
                    var hiddenSearch = document.getElementById("searchValue");
                    hiddenSearch.value = searchValue;
                    document.getElementById("searchServerForm").submit();
                }
            }
        }
    }
}
function searchNode2(node, strSearch) {
    if (node == null) {
        return;
    }
    if (node.tagName == "rentAddress") {
        var tmp = node.firstChild.nodeValue;
        var search = strSearch.trim().toLowerCase();
        if (tmp.toLowerCase().indexOf(search, 0) > -1) {
            // create view
            var divListHouse = document.getElementById('listHouse');
            var top_item = document.createElement("div");
            top_item.setAttribute("class", "top-item");
            divListHouse.appendChild(top_item);
            var item = document.createElement("div");
            item.setAttribute("class", "item");
            top_item.appendChild(item);
            var image_box = document.createElement("div");
            image_box.setAttribute("class", "image-box");
            item.appendChild(image_box);

            var item_price = document.createElement("div");
            item_price.setAttribute("class", "item-price");
            item.appendChild(item_price);

            var action = document.createElement("div");
            action.setAttribute("class", "action");
            item.appendChild(action);

            var browser = document.createElement("a");
            browser.setAttribute("target", "_blank");
            action.appendChild(browser);

            var button_link = document.createElement("input");
            button_link.setAttribute("type", "submit");
            button_link.setAttribute("value", "website");
            button_link.setAttribute("name", "action");
            button_link.setAttribute("class", "btnBrowse");
            browser.appendChild(button_link);

            var love_btn = document.createElement("input");
            love_btn.setAttribute("type", "submit");
            love_btn.setAttribute("value", "love");
            love_btn.setAttribute("name", "action");
            love_btn.setAttribute("class", "love");
            action.appendChild(love_btn);

            //create image tag
            var imgTag = document.createElement("img");
            imgTag.setAttribute("class", "image-box");
            image_box.appendChild(imgTag);
            var image = findNodeValue(node, "img", true);
            imgTag.setAttribute("src", image);
//            // create rentprice
            var rentPrice = findNodeValue(node, "rentPrice", false);
            item_price.innerHTML = rentPrice;

            var browser_link = findNodeValue(node, "linkNew", true);
            browser.setAttribute("href", browser_link);
        }
    }
    var childs = node.childNodes;
    for (var i = 0; i < childs.length; i++) {
        searchNode2(childs[i], strSearch);
    }
}

function findNodeValue(node, name, isPrevious) {
    if (isPrevious) {
        while (node.tagName != name) {
            var node = node.previousSibling;
        }
    } else {
        while (node.tagName != name) {
            var node = node.nextSibling;
        }
    }
    if (node.firstChild !== null) {
        return node.firstChild.nodeValue;
    } else {
        return "";
    }
}
