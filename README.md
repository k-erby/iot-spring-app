# assn3-javafx
JavaFX starter code

## Contribute to the Repo

### Creating a Branch and Making Changes

```bash
// Creating a branch
git checkout -b new-branch-name_solving_issue

// Make changes and see what files differ from master
git status

// Add modified files one at a time
git add {$specific file names}
// OR (Danger, danger! Do this sparingly) add ALL files at once
git add .
```

Once small changes have been made, commit and push them.
```bash
git commit -m "Description of Changes"
git push origin your-branch-name
```

When all changes are made, create a PR and wait for the review/approval process.

### Merging Code

```bash
// After committing changes, go to master
git checkout master

// Optional: done if someone has merged code between the creation of the branch and your changes, or 
// you haven't been rebasing your branch. These commands updates local master to origin master.
git fetch
git reset --hard origin/master
git checkout your-branch-name
git rebase origin/master         // Takes your changes and puts them on top of any new changes
git push origin your-branch-name // Update local branch if you've need needed to recieve changes
git checkout master              // Back to master to continue the merge

// Merge your branch from master
git merge --no-ff your-branch-name -m "Merge 'your-branch-name'"
git push origin master

// Optional: clean up your branches by deleting them (on git, then locally)
git push origin :your-branch-name // Double check master is OK before net step
git branch -d your-branch-name
```

See https://github.com/SENG330/course/blob/master/assignment3.md
