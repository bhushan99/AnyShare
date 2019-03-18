var app = angular.module("anyShare", ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/home", {
        templateUrl: "home.html"
    })
    .when("/", {
        templateUrl: "home.html"
    });
});

app.controller('mainController', function($scope) {
    $scope.appName = 'AnyShare!';
});

app.controller('homeController', function($scope, $http, $location, $route) {

    function loadProducts() {
        $scope.productsLoaded = false;
        $http.get('/getProducts')
        .then(function(response) {
            $scope.products = response.data;
            fillUnsoldProducts();
            fillUnsoldHistoryForUser();
            fillSoldHistoryForUser();
            $scope.productsLoaded = true;
        });
    }

    function fillUnsoldProducts() {
        $scope.productsUnsold = [];
        for(var i in $scope.products) {
            if($scope.products[i].status === 'UNSOLD') {
                $scope.productsUnsold.push($scope.products[i]);
            }
        }
    }

    function fillUnsoldHistoryForUser() {
        $scope.unsoldHistory = [];
        for(var i in $scope.products) {
            if($scope.products[i].seller === $scope.username && 
                    $scope.products[i].status === 'UNSOLD') {
                $scope.unsoldHistory.push($scope.products[i]);
            }
        }
    }

    function fillSoldHistoryForUser() {
        $scope.soldHistory = [];
        for(var i in $scope.products) {
            if($scope.products[i].seller === $scope.username && 
                    $scope.products[i].status === 'SOLD') {
                $scope.soldHistory.push($scope.products[i]);
            }
        }
    }

    function loadWishlist() {
        $scope.wishlistLoaded = false;
        $http.get('/getWishlist?username=' + $scope.username)
        .then(function(response) {
            $scope.wishlist = response.data;
            $scope.wishlistLoaded = true;
        });
    }

    $scope.action = 'buy';
    $scope.isValidUser = false;

    $scope.username = '';
    $scope.password = '';

    $scope.logIn = function() {
        $http.post('/validateUser', {
            username : $scope.username,
            password : $scope.password
        })
        .then(function(response) {
            if(response.data === true) {
                $scope.isValidUser = true;
                loadProducts();
                loadWishlist();
            }
            else {
                alert('Invalid credentials!');
            }
        });
    };

    $scope.signUpName = '';
    $scope.signUpEmail = '';
    $scope.signUpPhone = '';
    $scope.signUpAddress = '';
    $scope.signUpUsername = '';
    $scope.signUpPassword = '';
    $scope.signUpPasswordCnf = '';

    $scope.signUp = function() {
        $http.post('/addUser', {
            username : $scope.signUpUsername,
            password : $scope.signUpPassword,
            name : $scope.signUpName,
            email : $scope.signUpEmail,
            phone : $scope.signUpPhone,
            address : $scope.signUpAddress
        })
        .then(function(response) {
            if(response.data === true) {
                $scope.isValidUser = true;
                $scope.username = $scope.signUpUsername;
                loadProducts();
                loadWishlist();
            }
            else {
                alert('Could not sign up!');
            }
        },
        function(response) {
            alert(response.data.message);
        });
    };

    $scope.title = '';
    $scope.description = '';
    $scope.price = '';
    $scope.category = '';
    $scope.expiryDate = '';
    $scope.images = '';

    $scope.addProduct = function() {
        $scope.publishDate = new Date().toString();
        $scope.publishDate = $scope.publishDate.slice(0, $scope.publishDate.lastIndexOf(':'));

        $http.post('/addProduct', {
            title : $scope.title,
            description : $scope.description,
            price : ($scope.price ? Number($scope.price) : 0),
            category : $scope.category,
            expiryDate : $scope.expiryDate,
            seller : $scope.username,
            publishDate : $scope.publishDate,
            images : $scope.images.split(',')
        })
        .then(function(response) {
            if(response.data === true) {
                alert('Data saved successfully!');
                $scope.title = '';
                $scope.description = '';
                $scope.price = '';
                $scope.category = '';
                $scope.expiryDate = '';
                loadProducts();
            }
            else {
                alert('Could not sign up!');
            }
        },
        function(response) {
            alert(response.data.message);
        });
    };

    $scope.buyProduct = function(productId) {
        var uname = '';
        for(var i in $scope.products) {
            if($scope.products[i].id === productId) {
                uname = $scope.products[i].seller;
                break;
            }
        }

        $http.get('/getUser?username=' + uname)
        .then(function(response) {
            $http.get('/addToWishlist?id=' + productId + '&username=' + $scope.username)
            .then(function(response) {
                if(response.data) {
                    loadWishlist();
                }
            });
            alert('Please contact ' + response.data.name 
            + ' at email ' + response.data.email + ' or phone ' + response.data.phone);
        });
    };

    $scope.markAsSold = function(productId) {
        $http.get('/markAsSold?id=' + productId)
        .then(function(response) {
            if(response.data) {
                loadProducts();
                alert('Product marked as sold!');
            }
        });
    };

    $scope.logOut = function() {
        window.location.reload();
    };
});

app.controller('userController', function($scope, $routeParams) {
    $scope.username = $routeParams.username;

});
