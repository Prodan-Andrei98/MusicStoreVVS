(function(){
    'use strict'
    angular
        .module('app')
        .controller('InstrumentsController',InstrumentsController);
    InstrumentsController.$inject=['$http'];
    function InstrumentsController($http){
        var vm = this;
        vm.instruments=[];
        vm.getAll=getAll;
        vm.getAffordable=getAffordable;
        vm.deleteInstruments=deleteInstruments();
        init();

        function init(){
       getAll(); }

        function getAll(){

           var url="/allInstr";
            var productsPromise=$http.get(url);
            productsPromise.then(function(response){
                vm.instruments=response.data;
            });

        }
        function getAffordable(){

            var price;
            price = document.getElementById("affordablePrice").value;
            var url="/affordable/" + price;
            var productsPromise=$http.get(url);
            productsPromise.then(function(response){
                vm.instruments=response.data;
            });

        }
        function deleteInstruments(id){

            var url="/delete/" + id;
            $http.post(url).then(function(response){
                vm.instruments=response.data;
            });
        }
    }

})();