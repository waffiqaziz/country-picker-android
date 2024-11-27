# Contributing to Country Picker Android

Thank you for considering contributing to the Country Picker Android library! We welcome contributions to
improve the module, fix bugs, and add new features. Below is a step-by-step guide to help you get
started.

1. Fork the Repository
    - Start by [forking this repository](https://github.com/waffiqaziz/country-picker-android/fork)
      to your GitHub account. Clone the Forked Repository
2. Clone the forked repository to your local machine by running the following command:
   ```bash
   git clone https://github.com/yourusername/country-picker-android.git
   ```
   Replace `yourusername` with your GitHub username.
3. Create a New Branch
    - Before you start working on your changes, create a new branch to isolate your work:
      ```bash
      git checkout -b your-branch-name
      ```
    - Use a meaningful name for your branch, e.g., feature-country-search or bugfix-null-pointer.
4. Build and Run First
    - To ensure that no errors occur before the changes you make, make sure you  run `./gradlew testDebugUnitTest` and `./gradlew connectedAndroidTest` to run all the
      test case.

5. Make Your Changes
    - Open the project in your favorite Android IDE (like Android Studio).
    - Navigate to the `:country-picker` module and make your desired changes or improvements.
    - If you are adding a new feature or making significant changes, consider writing unit tests for
      your code or at least check it first if its working or not.
    - You can run `./gradlew testDebugUnitTest` and `./gradlew connectedAndroidTest` to run all the
      test case.
6. Commit Your Changes
    - Once you're done with your changes, commit them with a meaningful message:
      ```bash
      git add .
      git commit -m "Description of your changes"
      ```

7. Push to Your Forked Repository
    - Push your changes to your GitHub forked repository:
      ```bash
      git push origin your-branch-name
      ```
8. Create a Pull Request
    - Go to the original repository on GitHub and click on the "Compare & Pull Request" button.
    - Provide a clear description of your changes in the pull request and reference any related
      issues if applicable.
    - Once submitted, I will review your pull request.

### Contribution Guidelines

- Code Style: Ensure your code follows the Android best practices and coding standards.
- Testing: Add appropriate tests for your changes, especially if you're adding new functionality.
- Documentation: Update the documentation if your changes affect how the module is used.
- Issues: If you're fixing a bug or adding an enhancement, make sure to reference the issue number
  in your commit or PR description.

### Reporting Issues

If you encounter any issues or bugs, feel free to open a new issue in the repository and provide as
much detail as possible. Include steps to reproduce the issue, the expected behavior, and any
relevant logs.
