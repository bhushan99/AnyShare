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
    $scope.appName = 'AnyShare';
});

app.controller('homeController', function($scope, $http, $location, $route) {

    function loadProducts() {
        $scope.productsLoaded = false;
        $http.get('/getProducts')
        .then(function(response) {
            $scope.products = response.data;
            $scope.productsLoaded = true;
        });
    }

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

    $scope.title = '';
    $scope.description = '';
    $scope.price = '';
    $scope.category = '';
    $scope.expiryDate = '';
    $scope.addProduct = function() {
        $http.post('/addProduct', {
            title : $scope.title,
            description : $scope.description,
            price : $scope.price,
            category : $scope.category,
            expiryDate : $scope.expiryDate
        })
        .then(function(response) {
            if(response.data === true) {
                alert('Data saved successfully!');
                $scope.title = '';
                $scope.description = '';
                $scope.price = '';
                $scope.category = '';
                $scope.expiryDate = '';
            }
            else {
                alert('Could not sign up!');
            }
        },
        function(response) {
            alert(response.data.message);
        });
    };
});

app.controller('userController', function($scope, $routeParams) {
    $scope.username = $routeParams.username;

});