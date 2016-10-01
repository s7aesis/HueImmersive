## v0.6 (4 Sep 2016) ##
 - updated bridge login and user registration
 - add support for more hue bulb models
 - add transition time slider
 - add dependence check between startup arguments
 - add auto turn off threshold slider
 - reworked settings backend
 - reworked lights backend
 - improved automatic turn off algorithm
 - improved build tagging
 - updated jgoodies lib
 - fixed brightness when turning lights on
 - fixed typos

## v0.5 (24 Dec 2014) ##
 - add saturation slider
 - add new start parameters: force-on, force-off, force-start
 - add start parameters to option interface
 - tweaked some transition values
 - reordered color algorithms
 - light options are now saved with unique id
 - fixed on/off button bug
 - fixed problem with multiple startup parameters
 - fixed debug formation
 - fixed/prevent purple over-saturation
 - fixed connection if bridge ip changed
 - fixed problem at second start with unauthorized user
 - fixed default settings setup
 - code improvements

## v0.4.4.1 (24 Nov 2014) ##
 - fixed problem with different reply amounts depending on the firmware release

## v0.4.4 (26 Oct 2014) ##
 - program affects only 'Extended color lights'
 - add more / changed debug information
 - on/off button affects only activated lights
 - fixed some issues with changed light id's
 - fixed option interface crash if no lights have been detected
 - some changes to the option interface

## v0.4.3.1 (26 Oct 2014) ##
 - hotfix 'restore color' function with unsupported lights

## v0.4.3 (24 Oct 2014) ##
 - move the whole project to GitHub
 - latest version and changelog requested now from GitHub
 - change changelog formation
 - change 'about' text
 - add License
 - whole lot of class, variable, method renaming
 - clean up code

## v0.4.2 (18 Sep 2014) ##
 - add version notes to experimental features
 - add command-line arguments: debug, log, reset

## v0.4.1 (10 Sep 2014) ##
 - slightly faster startup
 - disable the 'capture screen' option if you have less than one screen
 - add color grid window title
 - set timeout for connections to bridge
 - individual brightness option will now be disabled when the light is deactivated
 - code optimizations

## v0.4 (9 Sep 2014) ##
 - add new color algorithm (average color)
 - connection data will now be saved (faster connection, faster startup)
 - add brightness option for each light
 - add tooltips
 - connection interface is now integrated into the main interface
 - some UI improvements
 - chunks slider is now exponentially
 - chunks are now more like squares
 - add experimental hint to 'restore light' function
 - 'auto. turn off lights' function is now slightly improved
 - great many code optimizations, changes and fixes

## v0.3.3 (31 Aug 2014) ##
 - Hue Immersive is now fully usable on OS X
 - changed some font sizes
 - changed some button sizes

## v0.3.2 (29 Aug 2014) ##
 - font size keeps the same no matter what DPI you're system set
 - custom UI colors are no longer system dependent
 - display names are now simplified
 - changed minimum number of chunks
 - changed changelog font size
 - number of chunks is now calculated correctly

## v0.3.1.3 (29 Aug 2014) ##
 - 'about menu' background color

## v0.3.1.2 (28 Aug 2014) ##
 - options will now be saved/loaded correctly
 - versions with more than three numbers will be now show correctly

## v0.3.1.1 (28 Aug 2014) ##
 - hotfix: all settings will be automatic deleted at start

## v0.3.1 (28 Aug 2014) ##
 - add option to select the screen that the program should capture
 - 'show color grid' option will now be saved
 - fixed capture and aspect ratio function
 - fixed color grid function

## v0.3 (26 Aug 2014) ##
 - add settings menu bar
 - add options window
 - add option to select the lights you want to use
 - add option to select one of three color algorithms for each light
 - add option to disable the automatic light switch
 - add option to turn off gamma correction
 - add reset function
 - 'restore light' option will now be saved
 - changed some 'Color picker' code
 - changed 'check for updates' window size
 - disable the automatic light switch by default
 - changed settings save directory
 - code optimizations

## v0.2.1 (22 Aug 2014) ##
 - the program now recognizes correctly a black/dark screen
 - better update code

## v0.2 (21 Aug 2014) ##
 - turn off lights when the screen is very dark
 - you can now search for new updates
 - you will be notified when a new update is available
 - add menu bar
 - add 'about' window
 - on/off buttons will be disabled while the program is running
 - 'aspect ratio' option will now be saved
 - changed project name

## v0.1 (20 Aug 2014) ##
 - initial release