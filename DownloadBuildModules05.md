_This material is distributed under the_<a href='http://www.gnu.org/licenses/gpl-2.0.html'>GNU General Public License Version 2</a>_._To obtain a copy of the original source code, make a request on the <a href='http://groups.google.com/group/openwonderland'>Wonderland Forum</a>_._


## Open Wonderland v0.5: Download, Build and Deploy Modules ##


This page describes how to download, build and deploy the optional add-on modules for Open Wonderland from the source code. The add-on modules contains additional functionality not found in the core workspace. Some of these add-on modules are compiled into the binary distributions of Open Wonderland.

If you would like to download binary modules, and do not need the module source, you can do so from the [Open Wonderland module warehouse](http://openwonderland.org/index.php?option=com_docman&cat=add_ons&Itemid=123).

### Requirements ###

Before downloading, compiling, and installing the optional add-on modules, you should have first downloaded and compiled the Open Wonderland v0.5 source. You can find instructions here:

  * [Download, Configure, Build and Run from the Wonderland v0.5 Source](DownloadBuildSource05.md)

### Download the Open Wonderland modules workspace ###

The **wonderland-modules** workspace contains all of the source code for the Open Wonderland modules and is available via **svn**. Note that this subversion workspace includes both the 0.4 modules and the 0.5 modules; the 0.5 module source is located under the **0.5/** directory and is broken up into two categories: **stable** and **unstable**. (Although "stable" is a relative term, since Open Wonderland itself is experimental software). For example, the "stable" modules currently include the SVG whiteboard and the audio recorder. The "unstable" modules are either examples or experimental modules: there is no guarantee they will work at any point in time.

The build system of Open Wonderland is designed so that you may compile and deploy each module individually. It is also designed so that if you place your **wonderland-modules** workspace alongside your **wonderland** workspace, the "stable" modules will also be compiled and certain ones appearing on a "white list" will also be deployed to the server when it is started.

This tutorial assumes you will place the modules workspace under a base directory named **~/src/wonderland**, although you are free to place it anywhere. It also assumes you have further placed the core Open Wonderland workspace under the **0.5-[r4260](https://code.google.com/p/openwonderland/source/detail?r=4260)/** subdirectory (for a particular stable revision, in this example 4260) or under the **trunk/** subdirectory (for the latest source code).

Below, you will place the **wonderland-modules** workspace as a sibling of the core **wonderland** workspace (that is, both directories have the same parent directory in the file system).

#### Latest module source code ####

To download the latest module source (trunk), inside a terminal window from your home directory, after you have created the **~/src/wonderland/trunk/** subdirectory:

```
% cd ~/src/wonderland/trunk
% svn checkout http://openwonderland-modules.googlecode.com/svn/trunk/0.5 wonderland-modules
```

#### Stable release module source code ####

If you would like to find the modules corresponding to a particular stable release, launch the stable release from the command line, and look for the release date on the first line of the output:

```
Launching Open Wonderland version 0.5 rev. 4260 (January 10, 2012)
```

To download the source for this release, check out the wonderland-modules project by date inside a terminal window from your home directory, after you have created the **~/src/wonderland/0.5-[r4260](https://code.google.com/p/openwonderland/source/detail?r=4260)/** subdirectory:

```
% cd ~/src/wonderland/0.5-r4260
% svn checkout -r {2012-01-10} http://openwonderland-modules.googlecode.com/svn/trunk/0.5 wonderland-modules
```

### Building the Open Wonderland modules ###

#### Build the "stable" modules ####

You can build the Open Wonderland "stable" modules, assuming you have placed the workspace as a sibling of the core Wonderland workspace, by running the **ant** command from the _core_ Wonderland workspace. For the latest source code (trunk):

```
% cd ~/src/wonderland/trunk/wonderland
% ant
```

and for a particular stable revision:

```
% cd ~/src/wonderland/0.5-r4260/wonderland
% ant
```

**Note:** This **ant** command is _not_ run from the **wonderland-modules** workspace, but rather from the **wonderland** workspace.

You may also build each "stable" module individually by executing the **ant** command within each "stable" module directory.

#### Build the "unstable" modules ####

You can build the Open Wonderland "unstable" modules by executing the **ant** command for each module individually. This tutorial uses the **shape-module-tutorial** "unstable" module in trunk as an example, but the instructions apply to any module beneath the **unstable** directory.

  * Change directories to **~/src/wonderland/trunk/wonderland-modules/unstable/shape-module-tutorial**.
  * Edit the **my.module.properties** file to make sure that the **wonderland.dir** property points to your installation of the core **wonderland** workspace. If you have arranged your **wonderland-modules** workspace as a sibling of the **wonderland** workspace as shown above, the proper value of **wonderland.dir** should be **../../../wonderland**.
  * Run the **ant** command from the **shape-module-tutorial** directory.

### Deploying the Open Wonderland modules ###

#### Deploy the "stable" modules that appear in the "white list" ####

By default, a certain set of "stable" modules are automatically deployed (i.e. installed) to Wonderland when you run the server (using **ant run-server** in the **wonderland** workspace), assuming you have placed the workspace as a sibling of the core Wonderland workspace.

The set of "stable" modules that are automatically deployed are found the **wonderland-modules/stable/build.xml** file, within the `<target name="-modules-stable-dist-copy-selected">...</target>` tags (see example below). If you wish to add a "stable" module to the "white list", then add a new `<file name="...">` line following the examples already present.

```
    <!--
         copy only the selected modules to the dist directory.  This is the
         default action, unless the modules.include.all property is set
    -->
    <target name="-modules-stable-dist-copy-selected" unless="modules.include.all">
        <copy todir="dist">
            <filelist dir=".">
                <file name="orientationworld/dist/orientationworld.jar"/>
                <file name="pdfviewer/dist/pdfviewer.jar"/>
                <file name="telepointer/dist/telepointer.jar"/>
                <file name="whiteboard/dist/whiteboard.jar"/>
                <file name="image-viewer/dist/imageviewer.jar"/>
                <file name="audiorecorder-module/dist/audiorecorder.jar"/>
                <file name="stickynote/dist/stickynote.jar"/>
            </filelist>
            <mapper type="flatten"/>
        </copy>
    </target>
```

#### Deploy the non-"white list" "stable" modules and "unstable" modules ####

You can deploy each "stable" module that does not appear in the "white list" and all of the "unstable" modules individually. (Note that you may also manually (re)deploy the "stable" modules that appear in the "white list" too using these instructions.)

Note that when you deploy a module, it will restart the Wonderland server. If you have changed any code in your module that runs inside of the Open Wonderland server, the transient world state is typically erased. If you wish to persist the world state, you must take a snapshot of your world state first. For more information, please visit the <a href='http://wiki.java.net/bin/view/Javadesktop/ProjectWonderlandServerAdministration05'>Wonderland Web-Based Administration Guide</a>.

To deploy a module, first make sure your Wonderland server is running, and then within the module directory:

```
% ant deploy
```

If you wish to uninstall the module later on, you may do so via the <a href='http://wiki.java.net/bin/view/Javadesktop/ProjectWonderlandServerAdministration05'>Wonderland Web-Based Administration Tool</a>.

### Some additional comments ###

Really, you can checkout any individual module to any directory you wish. The instructions above simply point out the "best practices" followed by the core Wonderland development team. So long as the **wonderland.dir** property in the **my.module.properties** file points to your core **wonderland** workspace, you should be able to compile and deploy the module.