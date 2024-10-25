This README provides step-by-step instructions to set up the environment, install necessary dependencies, and reproduce the main experimental results as described in the paper. Please ensure you have a compatible GPU for optimal training performance.

## Table of Contents
1. [Environment Setup](#environment-setup)
2. [Data Access](#data-access)
3. [Code Execution](#code-execution)
4. [GPU Management](#gpu-management)

---

## Environment Setup

### Step 1: Create a New Conda Environment
To ensure a clean environment, it is recommended to create a new Conda environment. Open Anaconda Prompt or your terminal, and run the following command:

```bash
conda create -n reproducibility_env python=3.8
```
### Step 2: Activate the Environment
Activate the environment with:

```bash
conda activate reproducibility_env
```
### Step 3: Install Python Libraries
Once the environment is active, install the necessary Python libraries. The primary libraries for this project are from Hugging Face, including huggingface_hub, transformers, and datasets.

```bash
pip install huggingface_hub transformers datasets
```

You can also install other dependencies from the requirements.txt file to ensure all packages are included:

```bash
pip install -r requirements.txt
```
### Step 4: Jupyter Notebook Setup 
If you intend to use Jupyter Notebooks, install jupyter within the environment:

```bash
pip install jupyter
```
###  Step 5: Launch Jupyter Notebook
Launch Jupyter Notebook by running:

```bash
jupyter notebook
```
## Data Access
### Step 1: Downloading Data
Access and download the dataset used in this paper from Hugging Face Dataset Hub. Load the data using the following code:

```bash
from datasets import load_dataset
dataset = load_dataset("dataset-name", split="train")
```
### Step 2: Data Preprocessing
Run the provided script to preprocess the data and prepare it for model training:

## Code Execution
### Step 1: Train the Model
To train the model, use the following command:

## GPU Management
### Step 1: Clear GPU Cache
To manage GPU memory between training and evaluation sessions, use the following Python command to clear the GPU cache:


```bash
import torch
torch.cuda.empty_cache()

```
### Step 2: Monitor GPU Memory Usage
Alternatively, monitor and manage GPU memory usage with nvidia-smi:

```bash
# To view current GPU memory usage
nvidia-smi


# To clear GPU memory if needed

sudo fuser -v /dev/nvidia*
```
For additional guidance, refer to the Hugging Face Transformers documentation or the code comments within each script.
https://huggingface.co/datasets
