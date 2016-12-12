var sudokuApp = angular.module('sudokuApp', []);

var ROOT_URL = 'http://localhost:8080/';

sudokuApp.directive('sudokuDirective', ['$http', function($http) {

    var sudokuController = function SudokuController($scope) {
        /**
         * possible values : 'INIT', 'INVALID', 'VALID'
         * @type {string}
         */
        $scope.status = 'INIT';

        $http.get(ROOT_URL + 'board').then(function (response) {
            var sudoku = response.data.sudoku;
            $scope.givenUp = false;

            /**
             * Initial board with all wholes. Should not be modified
             * @type {int[][][][]}
             */
            $scope.board = deepCopySudoku(sudoku);


            clearZeros(sudoku);
            /**
             * Current game with player modifications
             * @type {int|undefined[][][][]}
             */
            $scope.sudoku = sudoku;

        });

        $scope.checkSolution = function () {
            $http.post(ROOT_URL + 'validate', $scope.sudoku).then(function (response) {
                if (response.data.valid) {
                    $scope.status = 'VALID';
                } else {
                    $scope.status = 'INVALID';
                }

                $scope.validated = response.data.sudoku;
            });
        };

        $scope.showSolution = function () {
            $http.get(ROOT_URL + 'solution').then(function (response) {
                $scope.sudoku = response.data.sudoku;
                $scope.givenUp = true;
            });
        };

        function deepCopySudoku(sudoku) {
            return JSON.parse(JSON.stringify(sudoku));
        }

        function clearZeros(sudoku) {
            var SUDOKU_PART_SIZE = 3;

            for (var k1 = 0; k1 < SUDOKU_PART_SIZE; k1++) {
                for (var k2 = 0; k2 < SUDOKU_PART_SIZE; k2++) {
                    for (var k3 = 0; k3 < SUDOKU_PART_SIZE; k3++) {
                        for (var k4 = 0; k4 < SUDOKU_PART_SIZE; k4++) {
                            if (sudoku[k1][k2][k3][k4] == 0) {
                                sudoku[k1][k2][k3][k4] = undefined;
                            }
                        }
                    }
                }
            }
        }
    };

    return {
        templateUrl: 'sudoku-directive.html',
        restrict: 'E',
        controller: ['$scope', sudokuController],
    };
}]);