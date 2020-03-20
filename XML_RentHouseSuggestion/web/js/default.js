function displaySearchOptionSpace() {
//    document.getElementById("search_simple").style.height = '40%';
//    document.getElementById("search_simple").style.display = 'none';
//    document.getElementById("search_option").style.display = 'block';
    var heightDiv = document.getElementById("search_space");
    var btnMoreOption = document.getElementById("btn_MoreOption");
    if (heightDiv.style.height === '40%') {
        document.getElementById("search_space").style.height = '80%';
        btnMoreOption.style.background = "url('css/icon_doubleup.png') no-repeat right";
    } else {
        document.getElementById("search_space").style.height = '40%';
        btnMoreOption.style.background = "url('css/icon_doubledown.png') no-repeat right";
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
    }
}