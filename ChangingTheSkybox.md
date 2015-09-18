# Changing the Skybox #
**By Matthew Schmidt**


Here's a how-to for replacing the default skybox.

Caveat: AFAIK, you have to rebuild the Wonderland source code to get this to work. This will not work if you are using the binary build.


1. Download or build a skybox.

  * Pre built skyboxes can be found here: http://www.3delyvisions.com
  * You're on your own if you build it yourself. Plenty of tutorials and software is available online.

2. Navigate to the skybox directory:

```
%yourWonderlandDirectory%/wonderland/modules/world/defaultenvironment/art/skybox1
```

3. Make backup copies of all your existing skybox images

```
     -in bash:

          $ mkdir backup

          $ cp *.jpg ./backup
```

4. Name the new skybox files the same names as the old skybox files

The default Wonderland skybox names correspond to the following
directions:

```
	1.jpg = right
	2.jpg = back
	3.jpg = left
	4.jpg = front
	5.jpg = bottom
	6.jpg = top
```

Visually, they should be arranged like this:

![http://openwonderland.org/images/skyboxorder.png](http://openwonderland.org/images/skyboxorder.png)

Note that the top edge of image 4 must match up with the bottom edge of image 6.

5. Resize the new skybox images to dimensions 512 x 512

6. Overwrite the old skybox image files by copying the new skybox
images, which you renamed and resized, to the skybox directory. :

```
%yourWonderlandDirectory%/wonderland/modules/world/defaultenvironment/art/skybox1
```

NOTE: Many pre-built skyboxes do not have a bottom image. Since the
bottom image is typically not seen in-world, you do not have to
replace it. Just leave the old skybox image.

7. Rebuild Wonderland (ant clean, ant, ant run-server)

8. Launch and enjoy your new skybox!

![http://lh3.ggpht.com/_4iskP_xs9eE/TJqJ-q7uh6I/AAAAAAAAEN4/LlOS5t4lEnI/s800/Picture%202.png](http://lh3.ggpht.com/_4iskP_xs9eE/TJqJ-q7uh6I/AAAAAAAAEN4/LlOS5t4lEnI/s800/Picture%202.png)