
# RoutingUI

RoutingUI is a software entirely developed in  Java, executable on all compatible machines. Its purpose is to define the best routes based on the number of nodes to traverse and the optional cost of each connection. Its operation, based on the logic of a  weighted graph, represents the simplest way to represent the internet and the  global network  that now accompanies us day by day in our lives.

## User Interface

The user interface has been designed to be simple and intuitive, even for less experienced users. The approach used consists of, on one hand, managing networks and the path to be identified, and on the other hand, providing a graphical and instantaneous representation of the network, the selected nodes, and the identified path.

## Networks

From the "Networks" list, you can select the network on which you want to perform the simulation. Networks are loaded from the /Networks folder and can be modified at the user's discretion. To the right, the identifiers of the devices of the currently selected network are displayed.

## Simulation  Management

The user can select the two endpoints of the path through two drop-down input fields, then enter additional optional fields such as, for example, cost and maximum hops. Once the  simulation parametersare defined, the simulation can be started using the "Calculate path" button.

## Simulation Results

When the simulation is complete, the user can view the results in the last section. It is possible to select one of the valid paths found and view each stage, average costs, attempts made, etc.

## Graphical Representation  of Networks

On the right is the graphical representation of the network, which is generated instantly and simultaneously with the user's choices. The network nodes and the various connections between them are represented in blue. In red are the nodes chosen by the user and the connections identified by the  routing algorithm  to establish the connection.

## History, Development, and Evolution

The development of the project started in early March of the 2021/2022 school year, initially with the creation of software with a terminal user interface. The difficulty of this first development phase was designing a working and efficient routing algorithm that adhered to the given guidelines. Subsequently, the main dilemma was how to intuitively represent, within the terminal, the processed data. The identified paths are divided using different glyphs, making it easy to recognize the text that would otherwise be too cluttered and lead to a mechanical and non-fluid UX. With the evolution of the project, it was decided to opt for a proper user interface, as everyone understands it: a window with which you can interact using your mouse or trackpad. The  UI  in question was first designed and studied, then implemented with the  JavaFX  framework and the help of the Scene Builder software. The need for a user interface arose when a graphical representation of the networks became necessary to understand their geometry and make the possible solutions highlighted by the algorithm more immediate to everyone's eyes.

## Languages

The project was entirely developed in Java (SDK 11).  FXML  and  CSS files  are used to manage the user interface. The use of Java makes the application executable on any device with a configured  JVM, which means that the software is compatible with systems such as macOS, Linux, and Windows, among others.

## Frameworks

The routing algorithm was developed entirely independently, but for the user interface, the JavaFX framework ([https://openjfx.io](https://openjfx.io/)) was used. For the graphical representation of the network, the JavaFXSmartGraph library ([https://github.com/brunomnsilva/JavaFXSmartGraph.git](https://github.com/brunomnsilva/JavaFXSmartGraph.git)) has been utilized, although it has been extensively customized to the context and the developed project.

## Contact

If you have any questions or suggestions to improve RoutingUI, please do not hesitate to contact us at  [onnwen@garamante.it](mailto:onnwen@garamante.it). Thank you for your interest in our project!
