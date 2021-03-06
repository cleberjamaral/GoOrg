[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/cleberjamaral/autoOrgDesignProject)

# GoOrg - A meta-model for generating organisations for Multi-Agent Systems

GoOrg is a meta-model for automated generation of organisations ([PAAMS'19](https://link.springer.com/chapter/10.1007/978-3-030-24299-2_28) and [EMAS'19](http://cgi.csc.liv.ac.uk/~lad/emas2019/accepted/EMAS2019_paper_5.pdf) papers available). This demand comes from complex projects of multiple systems working in a coordinated way in order to achieve mutual goals which are situations where Multi-Agent Systems are often applied. To design such systems it is necessary to define how they will work together, i.e., how they will be organised which can be a tough task for humans encharged for the design of the whole system. To track this problem and help humans to easier develop Multi-Agent Systems we propose GoOrg for generating the organisational structure of the system.

## Running and testing

To run it use `./gradlew run`.

A graphviz representation (.gv file) of the input and of the output should be created in the folder `output/diagrams`. For search for one solution only, it is generated by default the PNG of the gdt and the solution in the folder `output/graphs`. Each time it runs the algorithm clean the output folders, so to keep the results it is necessary to make a copy before a running again.

In the class SimpleLogger there is a setting for printing output to standard console or to a file called `log.log` that is stored in the project root folder.

To run the simple embedded example with other cost functions and search approaches leave the first argument as "0" and use the second argument to select a cost function (see cost enumeration in javadocs for details). Currently the [search approach](#Search-approaches) used is BFS (Breadth First Search). Examples:

```
$ ./gradlew run --args="0 UNITARY"
```

To run other examples just refer to the corresponding moise organisational description file, choosing the cost function. It also supports multiple cost functions. In this case, the latter have higher priority. Examples:

```
$ ./gradlew run --args="examples/dsn.xml UNITARY"
```

It is available an integrated test of all cost functions among other unit tests. For running these tests getting text output, execute:

```
$ ./gradlew test -i
```

