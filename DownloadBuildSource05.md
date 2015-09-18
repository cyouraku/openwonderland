_This material is distributed under the_<a href='http://www.gnu.org/licenses/gpl-2.0.html'>GNU General Public License Version 2</a>_._To obtain a copy of the original source code, make a request on the <a href='http://groups.google.com/group/openwonderland'>Wonderland Forum</a>_._

## Open Wonderland v0.5: Download, Configure, Build and Run from the Source Code ##

This page describes how to download, configure, build and run Open Wonderland v0.5 from the source code. Generally only software developers will use Wonderland in this way -- to do so, you should be familiar and comfortable with building open-source software from the command-line using **ant** or via an IDE.

The Open Wonderland source code comes bundled with most of the software you will need, including an up-to-date version of Java 3D and Project Darkstar.

### Requirements ###

Wonderland is set up as a Netbeans project that includes ant scripts for running the various Wonderland services. If you're using Netbeans you can check out and compile the project directly. If not, you need to install the following tools if not already present on your system:

  1. Have Java SE 6 installed on your system.
  1. Have **ant** version 1.7.1 installed on your system (http://ant.apache.org/).
  1. Have **subversion** installed on your system (http://subversion.tigris.org/), if you plan to install the optional add-on modules.

If you wish to use the command-line in addition to Netbeans, be aware that Netbeans does not add **ant** to your path. You will need to do this manually.
If you are using Netbeans, the _wonderland_ workspace is setup as a Netbeans project. The Netbeans community has published tutorials on using Subversion ([here](http://www.netbeans.org/kb/60/ide/subversion.html)). You may invoke
the **ant** targets (specified below) directly from Netbeans.

**NOTE:** In general, you will need to set the **ANT\_OPTS** environment variable to **-XX:MaxPermSize=900m -Xmx900m** (otherwise you will likely get an OutOfMemoryError when trying to compile and run). For example, if you are using Bash, you can set the **ANT\_OPTS** environment variable using **export ANT\_OPTS="-XX:MaxPermSize=900m -Xmx900m"**.

### Download the _wonderland_ workspace ###

The _wonderland_ workspace contains all of the source code and any libraries that Open Wonderland depends upon and is available via **svn**. It contains its own ant-based build system. This tutorial assumes you will place this workspace inside of a directory named _~/src/wonderland_, although you are free to place it anywhere. (Note that '~' denotes your home directory).

#### Download the trunk ####

The most recent version of the code is called the _trunk_. To download the source in the trunk, inside a terminal window from your home directory, after you have created the _src/wonderland/_ subdirectory:

```
% cd ~/src/wonderland
% mkdir trunk
% cd trunk
% svn checkout http://openwonderland.googlecode.com/svn/trunk wonderland
```

This will download the Open Wonderland source into the directory _~/src/wonderland/trunk/wonderland_.

#### Download the source for a stable release ####

Most developers should use the trunk of Open Wonderland, which is typically quite stable. If you need to find the source corresponding to a particular stable release, start the stable release from the command line and look at the first line of output. It should be something like:

```
Launching Open Wonderland version 0.5 rev. 4260 (January 10, 2012)
```

You can checkout this specific version based on the revision number, inside a terminal window from your home directory, after you have created the _src/wonderland/_ subdirectory:

```
% cd ~/src/wonderland
% mkdir 0.5-r4260
% cd 0.5-r4260
% svn checkout -r 4260 https://openwonderland.googlecode.com/svn/trunk wonderland
```

This will download the Open Wonderland source into the directory _~/src/wonderland/0.5-[r4260](https://code.google.com/p/openwonderland/source/detail?r=4260)/wonderland_. Note that updating the source using _svn update_ will not make any changes. If you want to restore the trunk version, use _svn update -r HEAD_.


### Building Open Wonderland ###

You can build the Open Wonderland source code by running the **ant** command in a terminal window:

```
% ant
```

Run this command in the _wonderland_ directory created by checking out the source, either _~/src/wonderland/trunk/wonderland_ or _~/src/wonderland/0.5-[r4260](https://code.google.com/p/openwonderland/source/detail?r=4260)/wonderland_.

### Running Open Wonderland ###

You run Open Wonderland by using **ant** targets provided by the build system. When you run Wonderland, you are really starting a web server on your machine. This web server provides a number of functions: it supports a web-based administration interface that lets you start/stop the Wonderland server and associated services.

To run wonderland, in the _wonderland_ directory:

```
% ant run-server
```

Once you start Wonderland, you can administer the server via a web-based user interface. Please visit the following tutorial for more details:

  * [ProjectWonderlandWebAdministration05](http://wiki.java.net/bin/view/Javadesktop/ProjectWonderlandServerAdministration05)

### Including optional modules ###

You may also wish to include optional modules in your Wonderland build. For instructions on downloading and including the optional modules, see DownloadBuildModules05.