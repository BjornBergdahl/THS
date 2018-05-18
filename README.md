# Ticket Handling System (THS)

Description...

## Prerequisites and Installing


## Built With
NetBeans 8.x

## Known Issues

### Database and Model Issues
* Our choice of JDBC with Stored Procedures in our local database (MYSQL) made version control a bit more complex since we initially chose to handle them outside of Git/GitHub.
* The model-classes where not very complex but placing our business logic in one large class was a bad design choice. 
* No major considerations were made considering the security of the database and modell design, since it was outside our scope.

### JSF Issues

We have encountered severe problems in our JSF ManagedBean beacuase of bad design choices and lack of understanding of the initialization functions in JSF.
* Our Bean has lots of logic operations to locally form and sort tickets and personnel to give the right content. This leaves lots of vulnerabilities for the next problem.
* The JSF markdown in XHTML initializes its backing fields in ways we didn't expect and this caused problems when expanding from basic funtionallity to more complex dynamic functions.
``` XHTML
<h:selectOneMenu styleClass="m-1" value="#{THSMB.personnelNo}" >
    <f:selectItems value="#{THSMB.personnels}" var="personnel" 
                   itemLabel="#{personnel.lastName}, #{personnel.firstName}" itemValue="#{personnel.staffNo}"/>
</h:selectOneMenu>  
``` 
* The second view needed its own Bean-class but the first issue in this list made this complicated and the working version of task.xhtml had functions deviating from the expected such as unregulated stepping forward in the ticketslist and showing the wrong tasks on reload. 

### JavaFX Issues

## Authors
Björn Bergdahl
Andreas Kuoppa

## License and Contributing
