![alt text](https://androidweekly.net/issues/issue-601/badge)
<h1>The ViewMode's leaked collectors problem playground</h1>
Sample proyect to showcase the issue exposed in the Medium article: 
https://medium.com/adidoescode/the-viewmodels-leaked-flow-collectors-problem-239a327f4b56

<h2>What is in here?</h2>
A very simple app with an Ui made in compose, which displays a count that increments over time and a Refresh button that is supposed to start the count from the start.
The count is coming from a ViewModel that has the leaked collector problem. Pressing several times the Refresh button will produce unexpected results, leaking collectors and emitting more times than expected. 

A FixedViewModel with correct implementation is also included and just commenting one line in the Activity can be used to see how now it works properly.
 
