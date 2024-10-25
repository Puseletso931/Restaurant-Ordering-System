This README provides step-by-step instructions to set up the environment, install necessary dependencies, and reproduce the main experimental results as described in the paper. Please ensure you have a compatible GPU for optimal training performance.

## Table of Contents
1. [Environment Setup](#environment-setup)
2. [Data Access](#data-access)
3. [Code Execution](#code-execution)
4. [GPU Management](#gpu-management)

---

### Environment Setup

#### Step 1: Create a New Conda Environment
To ensure a clean environment, it is recommended to create a new Conda environment. Open Anaconda Prompt or your terminal, and run the following command:


conda create -n reproducibility_env python=3.8
Activate the environment with:

bash
Copy code
conda activate reproducibility_env
###Step 2: Install Python Libraries
###Once the environment is active, install the necessary Python libraries. The primary libraries for this project are from Hugging Face, including huggingface_hub, transformers, and datasets.

pip install huggingface_hub transformers datasets
You can also install other dependencies from the requirements.txt file to ensure all packages are included:

bash
Copy code
pip install -r requirements.txt
Step 3: Jupyter Notebook Setup (Optional)
If you intend to use Jupyter Notebooks, install jupyter within the environment:

bash
Copy code
pip install jupyter
Launch Jupyter Notebook by running:

bash
Copy code
jupyter notebook
Data Access
Downloading Data
Access and download the dataset used in this paper from Hugging Face Dataset Hub. Load the data using the following code:

python
Copy code
from datasets import load_dataset
dataset = load_dataset("dataset-name", split="train")
Data Preprocessing
Run the provided script to preprocess the data and prepare it for model training:

bash
Copy code
python preprocess_data.py --input_dir raw_data --output_dir processed_data
Code Execution
Train the Model
To train the model, use the following command:

bash
Copy code
python train_model.py --dataset_path processed_data --output_dir results
Evaluate the Model
Evaluate the trained model using:

bash
Copy code
python evaluate_model.py --model_path results/model_name --test_data_path processed_data/test
GPU Management
To manage GPU memory between training and evaluation sessions, use the following Python command to clear the GPU cache:

python
Copy code
import torch
torch.cuda.empty_cache()
Alternatively, monitor and manage GPU memory usage with nvidia-smi:

bash
Copy code
# To view current GPU memory usage
nvidia-smi

# To clear GPU memory if needed
sudo fuser -v /dev/nvidia*
For additional guidance, refer to the Hugging Face Transformers documentation or the code comments within each script.
