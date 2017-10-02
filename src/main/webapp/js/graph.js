$(function () {

    $.get("api/chore/chart", function( chartData ) {
        var choreValues = $("#chore-values");

        new Chart(choreValues, {
            type: 'bar',
            data: {
                labels: ["exercise", "meditation", "household", "business", "administrative"],
                datasets: [
                    {
                        label: "Count of activity",
                        backgroundColor: "#3e95cd",
                        data: chartData.countOfActivty
                    },
                    {
                        label: "Goal for activity",
                        backgroundColor: "#000080",
                        data: chartData.goalOfActivty
                    }
                ]
            },
            options: {
                title: {
                    display: true,
                    text: 'Number of goals completed'
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });


    });



});